package be.vamaralds.merode.instance

import arrow.core.Either
import be.vamaralds.merode.instance.AttributeValue
import be.vamaralds.merode.instance.AttributeValue.Companion.attributeValue
import be.vamaralds.merode.instance.ValueTypeError
import be.vamaralds.merode.model.Attribute
import kotlin.test.Test

class AttributeValueTest {
    @Test
    fun `Successfully Create AttributeValue`() {
        val strValue = attributeValue("George")
        assert(strValue.isRight()) { "Expected String value to be created, but it failed due to: ${(strValue as Either.Left).value}" }
        strValue as Either.Right
        assert(strValue.value.toString() == "George")

        val intValue = attributeValue(0)
        assert(intValue.isRight()) { "Expected Int value to be created, but it failed due to: ${(intValue as Either.Left).value}" }

        val floatValue = attributeValue(0.0f)
        assert(floatValue.isRight()) { "Expected Float value to be created, but it failed due to: ${(floatValue as Either.Left).value}" }

        val doubleValue = attributeValue(0.0)
        assert(doubleValue.isRight()) { "Expected Double value to be created, but it failed due to: ${(doubleValue as Either.Left).value}" }

        val booleanValue = attributeValue(true)
        assert(booleanValue.isRight()) { "Expected Boolean value to be created, but it failed due to: ${(booleanValue as Either.Left).value}" }
    }

    @Test
    fun `Fail to Create AttributeValue (Unknown Type)`() {
        val invalidValue = attributeValue(Attribute("name", Attribute.Type.String))
        assert(invalidValue.isLeft()) { "Expected AttributeValue creation to fail due to unknown type, but it succeeded" }
        invalidValue as Either.Left
    }
}