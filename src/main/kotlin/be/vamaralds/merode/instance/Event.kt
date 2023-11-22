package be.vamaralds.merode.instance

import arrow.core.Either
import arrow.core.raise.either
import be.vamaralds.merode.model.EventType
import java.lang.ClassCastException

data class Event(val type: EventType, val eventId: Long, val objectId: Long, val properties: Set<Property> = emptySet()) {
    private val propertiesByName: Map<String, Property> by lazy { properties.associateBy { it.attribute.name } }

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