package be.vamaralds.merode.instance

import arrow.core.Either
import arrow.core.raise.either
import be.vamaralds.merode.model.EventType
import java.lang.ClassCastException

/**
 * [Event]s are instances of [EventType]s.
 * @param type The [EventType] of this [Event].
 * @param eventId The unique id of this [Event].
 * @param objectId The id of the [BusinessObject] to which this [Event] is targeted.
 * @param properties The set of [Property] of this [Event]. There is one [Property] for each [Attribute] of the [type]. By default, all [Property]s are set to null.
 */
data class Event(val type: EventType, val eventId: Long, val objectId: Long, val properties: Set<Property> = emptySet()) {
    /**
     * A map of the [properties] of this [Event] by their [Attribute.name].
     */
    private val propertiesByName: Map<String, Property> by lazy { properties.associateBy { it.attribute.name } }

    /**
     * @return The [Property] named [propertyName] of this [Event], if it exists
     * @return A [PropertyNotFoundError] if the [Property] does not exist in this [Event]
     * @return A [PropertyTypeError] if the [Property] exists but its value is not of type [T]
     */
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
        val idPropStr = "eventId: $eventId"
        val objIdPropStr = "objectId: $objectId"
        val idAndProps = setOf(idPropStr, objIdPropStr) +
                properties.map { "${it.attribute.name}: ${it.value}" }
                    .joinToString(", ")
        val builder = StringBuilder("${type.name} $idAndProps")
        return builder.toString()
    }
}