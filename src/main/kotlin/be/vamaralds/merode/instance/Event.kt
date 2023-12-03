package be.vamaralds.merode.instance

import arrow.core.Either
import arrow.core.EitherNel
import arrow.core.nel
import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
import be.vamaralds.merode.api.Api
import be.vamaralds.merode.common.MerodeError
import be.vamaralds.merode.model.EventType
import be.vamaralds.merode.model.Model
import be.vamaralds.merode.serialization.JsonDeserializable
import be.vamaralds.merode.serialization.JsonSerializable
import be.vamaralds.merode.serialization.SerializationError
import be.vamaralds.merode.serialization.safe
import org.json.JSONException
import org.json.JSONObject
import java.lang.ClassCastException

/**
 * [Event]s are instances of [EventType]s.
 * @param type The [EventType] of this [Event].
 * @param eventId The unique id of this [Event].
 * @param objectId The id of the [BusinessObject] to which this [Event] is targeted.
 * @param properties The set of [Property] of this [Event]. There is one [Property] for each [Attribute] of the [type]. By default, all [Property]s are set to null.
 */
data class Event(val type: EventType, val eventId: Long, val objectId: Long, val properties: Set<Property> = emptySet(), val masterRefs: Map<String, Long> = emptyMap()): JsonSerializable {
    /**
     * A map of the [properties] of this [Event] by their [Attribute.name].
     */
    private val propertiesByName: Map<String, Property> by lazy { properties.associateBy { it.attribute.name } }

    /**
     * @return The [Property] named [propertyName] of this [Event], if it exists
     * @return A [PropertyNotFoundError] if the [Property] does not exist in this [Event]
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

    override fun toString(): String {
        return this.toJsonString()
    }

    companion object: JsonDeserializable<Event> {
        context(Model)
        override fun fromJsonString(json: String): Either<SerializationError, Event> = either {
            safe<Event>(json) {
                val eventTypeName = this.getString("type")
                val eventType = Api.eventHandler!!.model.eventTypes.find { it.name == eventTypeName } ?: raise(SerializationError("EventType $eventTypeName not found"))
                val eventId = try { this.getLong("eventId") } catch (e: JSONException) { -1L }
                val objectId = try { this.getLong("objectId") } catch(e: JSONException) { -1L }
                val propertiesMap = this.getJSONObject("properties").toMap()
                val properties = propertiesMap.map { (name, value) ->
                    val attribute = eventType.attributes.find { it.name == name } ?: raise(SerializationError("Attribute $name not found in EventType $eventTypeName"))
                    val property = Property.property(attribute, value)
                        .mapLeft { SerializationError(it.toString()) }
                        .bind()
                    name to property
                }.toMap()
                val masterRefs = this.getJSONObject("masters").toMap() as Map<String, Long>

                return@safe eventType(eventId, objectId, properties, masterRefs)
                    .mapLeft {
                        SerializationError(it.all.toString())
                    }
                    .bind()
            }.bind()
        }

    }

    override fun toJsonString(): String {
        val jsonObj = JSONObject()
        jsonObj.put("type", type.name)
        jsonObj.put("eventId", eventId)
        jsonObj.put("objectId", objectId)
        val propertiesJsonObj = JSONObject()
        properties.forEach { property ->
            propertiesJsonObj.put(property.attribute.name, property.value.value)
        }
        jsonObj.put("properties", propertiesJsonObj)
        val mastersJsonObj = JSONObject(masterRefs)
        jsonObj.put("masters", mastersJsonObj)

        return jsonObj.toString()
    }
}