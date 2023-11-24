package be.vamaralds.merode.instance

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import be.vamaralds.merode.model.Attribute
import kotlin.reflect.typeOf

/**
 * Represents the value of an [Attribute] in a [BusinessObject].
 * @param attribute The [Attribute] of which this [Property] is a value.
 * @param value The value of this [Property].
 */
data class Property(val attribute: Attribute, val value: AttributeValue) {
    companion object {
        /**
         * @return A new [Property] with the provided [attribute] and [value], if the value is of the corresponding [Attribute.Type].
         * @return A [PropertyTypeError] if the value is not of the corresponding [Attribute.Type].
         */
        fun<T> property(attribute: Attribute, value: T?): Either<PropertyTypeError, Property> = either {
            when {
                value is String? && attribute.type == Attribute.Type.String -> Property(attribute, AttributeValue.StringValue(value))
                value is Int? && attribute.type == Attribute.Type.Int -> Property(attribute, AttributeValue.IntValue(value))
                value is Float? && attribute.type == Attribute.Type.Float -> Property(attribute, AttributeValue.FloatValue(value))
                value is Double? && attribute.type == Attribute.Type.Double -> Property(attribute, AttributeValue.DoubleValue(value))
                value is Boolean? && attribute.type == Attribute.Type.Boolean -> Property(attribute, AttributeValue.BooleanValue(value))
                else -> raise(PropertyTypeError(attribute.name, attribute.type.toString(), value?.let { it::class.simpleName ?: "Unknown" } ?: "Unknown"))
            }
        }
    }
}