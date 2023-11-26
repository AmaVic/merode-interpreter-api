package be.vamaralds.merode.instance

import arrow.core.EitherNel
import arrow.core.nel
import arrow.core.raise.either
import arrow.core.raise.ensure
import be.vamaralds.merode.model.EventType
import be.vamaralds.merode.model.Model
import be.vamaralds.merode.store.BusinessObjectStore
import be.vamaralds.merode.store.EventStore

class EventHandler(private val model: Model, private val eventStore: EventStore, private val objectStore: BusinessObjectStore) {
    /**
     * Attempts to handle the given [event] by applying it to the [BusinessObject] to which it is targeted.
     * If the [event] is successfully applied, the affected [BusinessObject]s are stored/updated in the [BusinessObjectStore] and the [event] is stored in the [EventStore].
     * If the [event] is not successfully applied, the [event] is not stored and the [BusinessObject]s are not updated.
     * @return A [List] of [BusinessObject]s that were affected by the [event] if the operation was successful
     * @return A [List] of [EventHandlingError]s if the operation was not successful
     */
    suspend fun handleEvent(event: Event): EitherNel<EventHandlingError, List<BusinessObject>> =
        when (event.type.ownerEffect) {
            EventType.OwnedEffect.Create -> handleCreateEvent(event)
            EventType.OwnedEffect.Modify -> handleModifyEvent(event)
            EventType.OwnedEffect.End -> handleEndEvent(event)
        }

    private suspend fun handleCreateEvent(event: Event): EitherNel<EventHandlingError, List<BusinessObject>> = either {
        ensure(event.type.ownerEffect == EventType.OwnedEffect.Create) { EventHandlingError("Event ${event.type.name} is not a create event").nel() }
        val ObjectType = model.objectTypes.find { it.name == event.type.ownerType.name }!!
        var newObject = ObjectType().mapLeft { EventHandlingError("Could not create new object of type ${ObjectType.name}").nel() }.bind()

        //Event handling
        newObject = newObject.handleEvent(event).bind()

        //Store new object
        newObject = objectStore.addNew(newObject)
            .mapLeft { EventHandlingError("Could not store new object of type ${ObjectType.name}").nel() }
            .bind()

        //Store event
        val newEvent = event.copy(objectId = newObject.id)
        eventStore.append(newEvent)
            .mapLeft { EventHandlingError("Could not store event ${newEvent}").nel() }
            .bind()

        listOf(newObject)
    }

    private suspend fun handleModifyEvent(event: Event): EitherNel<EventHandlingError, List<BusinessObject>> = either {
        ensure(event.type.ownerEffect == EventType.OwnedEffect.Modify || event.type.ownerEffect == EventType.OwnedEffect.End) { EventHandlingError("Event ${event.type.name} is not a modify event").nel() }
        val objectToModify = objectStore.get(event.objectId)
            .mapLeft { EventHandlingError("Could not retrieve object with id ${event.objectId} from object store").nel() }
            .bind()

        //Event handling
        val modifiedObject = objectToModify.handleEvent(event)
            .mapLeft { EventHandlingError("Could not modify object with id ${event.objectId}").nel() }
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