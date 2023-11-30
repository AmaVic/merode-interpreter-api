package be.vamaralds.merode.instance

import arrow.core.*
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.mapOrAccumulate
import arrow.core.raise.zipOrAccumulate
import arrow.fx.coroutines.parMapOrAccumulate
import be.vamaralds.merode.api.Api
import be.vamaralds.merode.instance.Property.Companion.property
import be.vamaralds.merode.model.Attribute
import be.vamaralds.merode.model.BusinessObjectType
import be.vamaralds.merode.model.State
import be.vamaralds.merode.serialization.JsonDeserializable
import be.vamaralds.merode.serialization.JsonSerializable
import be.vamaralds.merode.serialization.SerializationError
import be.vamaralds.merode.serialization.safe
import kotlinx.serialization.json.JsonObject
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
    private val properties: Set<Property> = emptySet(),
): JsonSerializable{
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
        val property = propertiesByName[propertyName] ?: raise(PropertyNotFoundError(propertyName, type.name))

        val typedValue = try {
            property.value.value?.let { it as T }
        } catch (e: ClassCastException) {
            raise(PropertyTypeError(propertyName, type.name, property.value.value!!::class.simpleName ?: "Unknown"))
        }

        typedValue
    }

    /**
     * This method applies an [Event] to this [BusinessObject], potentially changing its [state] and [properties].
     * The new [state] is derived by using the [type]'s [StateMachine] to determine the next [State] for the [state] and [Event.type].
     * The new property values are updated if they are provided in the [Event.properties] map. Otherwise, the old values are kept.
     *
     * @return A new [BusinessObject] with the updated [state] and [properties].
     * @return A [NonEmptyList] of [EventHandlingError]s if the [Event] cannot be applied to this [BusinessObject].
     */
    fun handleEvent(event: Event): EitherNel<EventHandlingError, BusinessObject> = either {
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

        val nextStateOp: Either<EventHandlingError, State> =
            type.stateMachine?.nextState(state, event.type)
                ?: EventHandlingError("No state machine defined for type ${type.name}").left()

        zipOrAccumulate(
            { resultingProperties.bind().toSet() },
            { nextStateOp.bind() }
        ) { p, s ->
            this@BusinessObject.copy(id = event.objectId, properties = p, state = s)
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
        return obj.toString()
    }

    companion object: JsonDeserializable<BusinessObject> {
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

                return@safe type(id, state, properties.values.toSet())
                    .mapLeft {
                        SerializationError(it.all.toString())
                    }
                    .bind()
            }.bind()
        }
    }
}