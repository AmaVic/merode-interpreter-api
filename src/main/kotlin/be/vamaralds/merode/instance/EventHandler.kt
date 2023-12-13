package be.vamaralds.merode.instance

import arrow.core.EitherNel
import arrow.core.mapOrAccumulate
import arrow.core.nel
import arrow.core.raise.either
import arrow.core.raise.ensure
import be.vamaralds.merode.model.EventType
import be.vamaralds.merode.model.Model
import be.vamaralds.merode.store.BusinessObjectStore
import be.vamaralds.merode.store.EventStore
import io.github.oshai.kotlinlogging.KotlinLogging

class EventHandler(val model: Model, val eventStore: EventStore, val objectStore: BusinessObjectStore) {
    private val logger = KotlinLogging.logger {  }
    /**
     * Attempts to handle the given [event] by applying it to the [BusinessObject] to which it is targeted.
     * If the [event] is successfully applied, the affected [BusinessObject]s are stored/updated in the [BusinessObjectStore] and the [event] is stored in the [EventStore].
     * If the [event] is not successfully applied, the [event] is not stored and the [BusinessObject]s are not updated.
     * @return A [List] of [BusinessObject]s that were affected by the [event] if the operation was successful
     * @return A [List] of [EventHandlingError]s if the operation was not successful
     */
    suspend fun handleEvent(event: Event): EitherNel<EventHandlingError, List<BusinessObject>> = either {
        logger.info { "Handling Event $event" }
        val affectedObjects = when (event.type.ownerEffect) {
            EventType.OwnedEffect.Create -> handleCreateEvent(event)
            EventType.OwnedEffect.Modify -> handleModifyEvent(event)
            EventType.OwnedEffect.End -> handleEndEvent(event)
        }.bind()
        logger.info { "Event Successfully Handled. Affected Objects: $affectedObjects" }
        affectedObjects
    }.mapLeft {
        logger.error { "Event Handling Failed: ${it.all}" }
        it
    }

    private suspend fun propagateEvent(fromObject: BusinessObject, event: Event): EitherNel<EventHandlingError, Map<String, BusinessObject>> = either {
        val masters = fromObject.masters
        logger.info { "Propagating Event $event from object: ${fromObject.toJsonString()} to masters: ${masters.map { it.value.type.name}}" }

        val mastersWithEvent = masters.mapOrAccumulate {
            val masterEvent = event.copy(objectId = it.value.id, masterRefs = emptyMap(), type = event.type.copy(ownerEffect = EventType.OwnedEffect.Modify), properties = emptySet())
            logger.info { "Specialized as ${masterEvent.toJsonString()} for ${it.value.type.name}"}

            val updatedMaster = it.value.handleEvent(objectStore, masterEvent).bindNel()
            updatedMaster.also { logger.info { "Updated Master: ${updatedMaster.toJsonString()}" }}
        }.bind()

        mastersWithEvent
    }

    private suspend fun handleCreateEvent(event: Event): EitherNel<EventHandlingError, List<BusinessObject>> = either {
        ensure(event.type.ownerEffect == EventType.OwnedEffect.Create) { EventHandlingError("Event ${event.type.name} is not a create event").nel() }
        val ObjectType = model.objectTypes.find { it.name == event.type.ownerType.name }!!
        var newObject = ObjectType().mapLeft { EventHandlingError("Could not create new object of type ${ObjectType.name}").nel() }.bind()

        //Event handling
        newObject = newObject.handleEvent(this@EventHandler.objectStore, event).bind()

        //Propagate
        val updatedObjects = propagateEvent(newObject, event).bind()
        updatedObjects.forEach {
            if(newObject.masters.containsKey(it.key))
                newObject.masters[it.key] = it.value
        }

        val storedUpdatedObjects = objectStore.update(updatedObjects.values)
            .mapLeft { EventHandlingError(it.toString()).nel() }
            .bind()

        //Store new object
        newObject = objectStore.addNew(newObject)
            .mapLeft { EventHandlingError("Could not store new object of type ${ObjectType.name}").nel() }
            .bind()

        //Store event
        val newEvent = event.copy(objectId = newObject.id)
        eventStore.append(newEvent)
            .mapLeft { EventHandlingError("Could not store event ${newEvent}").nel() }
            .bind()

        listOf(newObject) + storedUpdatedObjects
    }

    private suspend fun handleModifyEvent(event: Event): EitherNel<EventHandlingError, List<BusinessObject>> = either {
        ensure(event.type.ownerEffect == EventType.OwnedEffect.Modify || event.type.ownerEffect == EventType.OwnedEffect.End) { EventHandlingError("Event ${event.type.name} is not a modify event").nel() }
        logger.info { "Handling Modify Event ($event) on ${event.objectId}" }
        val objectToModify = objectStore.get(event.objectId)
            .mapLeft { EventHandlingError("Could not retrieve object with id ${event.objectId} from object store").nel() }
            .bind()
        logger.info { "  -> Retrieved Object: ${objectToModify.toJsonString()}" }


        //Event handling
        val modifiedObject = objectToModify.handleEvent(objectStore, event)
            .mapLeft { EventHandlingError("Could not modify object with id ${event.objectId}: ${it.all}").nel() }
            .bind()
        logger.info { "  -> Object after applying event: ${modifiedObject.toJsonString()}"}

        //Propagating
        val updatedObjects = propagateEvent(modifiedObject, event).bind()
        updatedObjects.forEach {
            if(modifiedObject.masters.containsKey(it.key))
                modifiedObject.masters[it.key] = it.value
        }

        val storedUpdatedObjects = objectStore.update(updatedObjects.values)
            .mapLeft { EventHandlingError(it.toString()).nel() }
            .bind()

        //Store modified object
        objectStore.update(modifiedObject)
            .mapLeft { EventHandlingError("Could not store modified object with id ${event.objectId}").nel() }
            .bind()

        //Store event
        eventStore.append(event)
            .mapLeft { EventHandlingError("Could not store event ${event}").nel() }
            .bind()


        listOf(modifiedObject)
    }

    private suspend fun handleEndEvent(event: Event): EitherNel<EventHandlingError, List<BusinessObject>> =
        handleModifyEvent(event)
}