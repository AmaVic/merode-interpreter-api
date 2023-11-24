package be.vamaralds.merode.instance

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import be.vamaralds.merode.model.BusinessObjectType
import be.vamaralds.merode.model.Attribute

/**
 * [BusinessObjectType]s are instantiated as [BusinessObject]s. Each [BusinessObjectType] has [Attribute]s, instantiated as [AttributeValue]s in  [BusinessObject]s.
 */
sealed class AttributeValue {
    abstract val value: Any?
    final override fun toString(): String = value.toString()

    data class StringValue(override val value: String?) : AttributeValue()
    data class IntValue(override val value: Int?) : AttributeValue()
    data class FloatValue(override val value: Float?) : AttributeValue()
    data class DoubleValue(override val value: Double?) : AttributeValue()
    data class BooleanValue(override val value: Boolean?) : AttributeValue()

    companion object {
        /**
         * Creates a new [AttributeValue] from the provided [value].
         * @return A [StringValue] (of [value]) if [value] is a [String].
         * @return An [IntValue] (of [value]) if [value] is an [Int].
         * @return A [FloatValue] (of [value]) if [value] is a [Float].
         * @return A [DoubleValue] (of [value]) if [value] is a [Double].
         * @return A [BooleanValue] (of [value]) if [value] is a [Boolean].
         * @return A [ValueTypeError] if [value] is not a valid attribute value type.
         */
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