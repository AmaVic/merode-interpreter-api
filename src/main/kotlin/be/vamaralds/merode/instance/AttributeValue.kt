package be.vamaralds.merode.instance

import arrow.core.Either
import arrow.core.left
import arrow.core.right

sealed class AttributeValue {
    abstract val value: Any?
    final override fun toString(): String = value.toString()

    data class StringValue(override val value: String?) : AttributeValue()
    data class IntValue(override val value: Int?) : AttributeValue()
    data class FloatValue(override val value: Float?) : AttributeValue()
    data class DoubleValue(override val value: Double?) : AttributeValue()
    data class BooleanValue(override val value: Boolean?) : AttributeValue()

    companion object {
        fun<T> attributeValue(value: T?): Either<ValueTypeError, AttributeValue> = when(value) {
            is String -> StringValue(value).right()
            is Int -> IntValue(value).right()
            is Float -> FloatValue(value).right()
            is Double -> DoubleValue(value).right()
            is Boolean -> BooleanValue(value).right()
            else -> ValueTypeError("The type of $value is not a valid attribute value type").left()
        }
    }
}