package be.vamaralds.merode.instance

import arrow.core.*
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.mapOrAccumulate
import arrow.core.raise.zipOrAccumulate
import be.vamaralds.merode.api.Api
import be.vamaralds.merode.common.MerodeError
import be.vamaralds.merode.instance.Property.Companion.property
import be.vamaralds.merode.model.*
import be.vamaralds.merode.serialization.JsonDeserializable
import be.vamaralds.merode.serialization.JsonSerializable
import be.vamaralds.merode.serialization.SerializationError
import be.vamaralds.merode.serialization.safe
import be.vamaralds.merode.store.BusinessObjectStore
import org.json.JSONObject
import java.lang.ClassCastException

/**
 * [BusinessObject]s are instances of [BusinessObjectType]s.
 * @param type The [BusinessObjectType] of this [BusinessObject].
 * @param id The unique ID of this [BusinessObject].
 * @param state The current [State] of this [BusinessObject].
 * @param properties The set of [Property] of this [BusinessObject]. There is one [Property] for each [Attribute] of the [type]. By default, all [Property]s are set to null.
 */
data class BusinessObject(
    val type: BusinessObjectType,
    val id: Long,
    val state: State,
    val properties: Set<Property> = emptySet(),
    val masters: MutableMap<String, BusinessObject> = mutableMapOf()
): JsonSerializable {
    /**
     * A map of the [properties] of this [BusinessObject] by their [Attribute.name].
     */
    private val propertiesByName: Map<String, Property> by lazy { properties.associateBy { it.attribute.name } }

    /**
     * @return The [Property] named [propertyName] of this [BusinessObject], if it exists
     * @return A [PropertyNotFoundError] if the [Property] does not exist in this [BusinessObject]
     * @return A [PropertyTypeError] if the [Property] exists but its value is not of type [T]
     */
    @Suppress("UNCHECKED_CAST")
    operator fun<T> get(propertyName: String): Either<InstanceError, T?> = either {
        val foundProperty = propertiesByName.containsKey(propertyName)

        if(!foundProperty)
            raise(PropertyNotFoundError(propertyName, this@BusinessObject.type.name))

        val foundValue: Property = propertiesByName[propertyName]!!

        try {
            return@either foundValue.value.value as T
        } catch (e: ClassCastException) {
            raise(PropertyTypeError(propertyName, type.name, foundValue::class.simpleName ?: "Unknown"))
        }
    }

    fun master(dependencyName: String): Either<InstanceError, BusinessObject> = either {
        ensure(masters.containsKey(dependencyName)) { raise(InstanceError("Master for dependency $dependencyName not found in this object of type (${type.name}")) }
        masters[dependencyName]!!
    }

    /**
     * This method applies an [Event] to this [BusinessObject], potentially changing its [state] and [properties].
     * The new [state] is derived by using the [type]'s [StateMachine] to determine the next [State] for the [state] and [Event.type].
     * The new property values are updated if they are provided in the [Event.properties] map. Otherwise, the old values are kept.
     *
     * @return A new [BusinessObject] with the updated [state] and [properties].
     * @return A [NonEmptyList] of [EventHandlingError]s if the [Event] cannot be applied to this [BusinessObject].
     */
    suspend fun handleEvent(objectStore: BusinessObjectStore, event: Event): EitherNel<EventHandlingError, BusinessObject> = either {
        ensure(event.objectId == id) { EventHandlingError("Event $event is not targeted at this object").nel() }

        val currentProperties = this@BusinessObject.properties.associateBy { it.attribute.name }
        val eventProperties = event.properties.associateBy { it.attribute.name }
        val propertiesNames = currentProperties.keys
        val propertiesValues = propertiesNames.map { propName ->
            val oldValue = currentProperties[propName]!!.value
            val eventValue = eventProperties[propName]?.value
            eventValue ?: oldValue
        }

        val resultingPropertiesOp = propertiesNames.zip(propertiesValues)
        val resultingProperties = resultingPropertiesOp.mapOrAccumulate {
            val (propName, propValue) = it
            val attribute = type.attribute(propName)
                .mapLeft { EventHandlingError(it.toString()) }
                .bind()
            property(attribute, propValue.value).bind()
        }.mapLeft {
            it.map { EventHandlingError(it.toString()) }
        }

        val masterRefs = event.masterRefs
        val masters = if(event.type.ownerEffect == EventType.OwnedEffect.Modify || event.type.ownerEffect == EventType.OwnedEffect.End)
            this@BusinessObject.masters.right()
        else
            masterRefs.mapOrAccumulate {
                val (depName, id) = it
                val fetchedObject = objectStore.get(id)
                    .mapLeft { EventHandlingError(it.toString()) }
                    .bind()
                depName to fetchedObject
            }.map {
                it.values.toMap()
            }

        val nextStateOp: Either<EventHandlingError, State> =
            type.stateMachine?.nextState(state, event.type)
                ?: EventHandlingError("No state machine defined for type ${type.name}").left()

        zipOrAccumulate(
            { resultingProperties.bind().toSet() },
            { nextStateOp.bind() },
            { masters.bind() }
        ) { p, s, m ->
            this@BusinessObject.copy(id = event.objectId, properties = p, state = s, masters = m.toMutableMap())
        }
    }

    override fun toString(): String {
        val idPropStr = "id: $id"
        val statePropStr = "state: ${state.name}"
        val idAndStateAndProps = setOf(idPropStr, statePropStr) +
                properties.map { "${it.attribute.name}: ${it.value}" }
                    .joinToString(", ")
        val builder = StringBuilder("${type.name} $idAndStateAndProps")
        return builder.toString()
    }

    override fun toJsonString(): String {
        val objMap = mapOf(
            "id" to id,
            "type" to type.name,
            "state" to state.name,
            "properties" to properties.associate { it.attribute.name to it.value.value }
        )

        val obj = JSONObject(objMap)

        val mastersObject = JSONObject()
        masters.forEach { depName, master ->
            val jsonMaster = master.toJsonString()
            val jsonObjectMaster = JSONObject(jsonMaster)
            mastersObject.put(depName, jsonObjectMaster)
        }

        obj.put("masters", mastersObject)

        return obj.toString()
    }

    companion object: JsonDeserializable<BusinessObject> {
        context(Model)
        override fun fromJsonString(json: String): Either<SerializationError, BusinessObject> = either {
            safe(json) {
                val id = this.getLong("id")

                val typeName = this.getString("type")
                val type = Api.eventHandler!!.model.objectType(typeName)
                    .mapLeft { SerializationError(it.toString()) }
                    .bind()

                val stateName = this.getString("state")
                val state = type.stateMachine!!.states().find { it.name == stateName }
                    ?: raise(SerializationError("State $stateName not found in state machine for type $typeName"))
                val propertiesMap = this.getJSONObject("properties").toMap()
                val properties = propertiesMap.map { (name, value) ->
                    val attribute = type.attributes.find { it.name == name } ?: raise(SerializationError("Attribute $name not found in ObjectType $typeName"))
                    val property = Property.property(attribute, value)
                        .mapLeft { SerializationError(it.toString()) }
                        .bind()
                    name to property
                }.toMap()

                val mastersMap = this.getJSONObject("masters").toMap()
                val masters = mastersMap.map {
                    val depName = it.key
                    val masterJson = it.value.toString()
                    val master = BusinessObject.fromJsonString(masterJson).bind()
                    depName to master
                }.toMap()

                return@safe type(id, state, masters, properties.values.toSet())
                    .mapLeft {
                        SerializationError(it.all.toString())
                    }
                    .bind()
            }.bind()
        }
    }
}