package be.vamaralds.merode.instance

import arrow.core.Either
import be.vamaralds.merode.instance.Property
import be.vamaralds.merode.model.Attribute
import kotlin.test.Test

class PropertyTest {
    private val strAttribute = Attribute("name", Attribute.Type.String)

    @Test
    fun `Successfully Create Property`() {
        val prop = Property.property(strAttribute, "George")
        assert(prop.isRight()) { "Expected property to be created successfully, but it failed due to: ${(prop as Either.Left).value}" }
    }

    @Test
    fun `Fail to Create Property (Type Mismatch)`() {
        val prop = Property.property(strAttribute, 0)
        assert(prop.isLeft()) { "Expected property creation to fail due to type mismatch, but it succeeded" }
    }
}