package be.vamaralds.merode.model

import arrow.core.Either
import be.vamaralds.merode.instance.MissingPropertyError
import be.vamaralds.merode.instance.PropertyNotFoundError
import kotlin.test.Test

class BusinessObjectTypeTest {
    private val Customer = testObjectType()

    @Test
    fun `Successfully Retrieve BOT Attribute`() {
        val attributeRetrieval = Customer.attribute("name")
        assert(attributeRetrieval.isRight())
    }

    @Test
    fun `Fail to retrieve BOT Attribute`() {
        val attributeRetrieval = Customer.attribute("unknown")
        assert(attributeRetrieval.isLeft())
    }

    @Test
    fun `Successfully Create BOT Instance - Ordered Values`() {
        val cst = Customer(0, State.Initial, "George", true, 0.1, null, 30, null)
        assert(cst.isRight()) { "The BOT Instance should be created but failed due to: ${(cst as Either.Left).value}"}
        cst as Either.Right

        assert(cst.value.id == 0L) { "Expected value for id is 0, obtained value is ${cst.value.id}" }

        val name = cst.value.get<String>("name")
        assert(name is Either.Right)
        name as Either.Right
        assert(name.value == "George") { "Expected value for name is George, obtained value is ${name.value}" }

        val isPremium = cst.value.get<Boolean>("isPremium")
        assert(isPremium is Either.Right)
        isPremium as Either.Right
        assert(isPremium.value != null) { "Expected value for isPremium is true, obtained value is ${isPremium.value}" }
        assert(isPremium.value!!) { "Expected value for isPremium is true, obtained value is ${isPremium.value}" }

        val discountPct = cst.value.get<Double>("discountPct")
        assert(discountPct is Either.Right)
        discountPct as Either.Right
        assert(discountPct.value == 0.1) { "Expected value for discountPct is 0.1, obtained value is ${discountPct.value}" }

        val deliveryDiscountPc = cst.value.get<Float>("deliveryDiscountPc")
        assert(deliveryDiscountPc is Either.Right)
        deliveryDiscountPc as Either.Right
        assert(deliveryDiscountPc.value == null) { "Expected value for deliveryDiscountPc is null, obtained value is ${deliveryDiscountPc.value}" }

        val age = cst.value.get<Int>("age")
        assert(age is Either.Right)
        age as Either.Right
        assert(age.value == 30) { "Expected value for age is 30, obtained value is ${age.value}" }
    }

    @Test
    fun `Failed to Create BOT Instance - Ordered Values - Missing Property`() {
        val cst = Customer(0, State.Initial, "George", true, 0.1, null, null)
        assert(cst.isLeft())
        cst as Either.Left

        assert(cst.value.filterIsInstance<MissingPropertyError>().size == 1) { "Expected 1 MissingPropertyError, obtained ${cst.value.filter { it is MissingPropertyError }.size}" }
    }

    @Test
    fun `Successfully Create BOT Instance - Key-Value Pairs`() {
        val cst = Customer(0, State.Initial, mapOf(
            "name" to "George",
            "isPremium" to true,
            "discountPct" to 0.1,
            "deliveryDiscountPc" to null,
            "age" to 30
        ))

        assert(cst.isRight())
        cst as Either.Right

        assert(cst.value.id == 0L) { "Expected value for id is 0, obtained value is ${cst.value.id}" }

        val name = cst.value.get<String>("name")
        assert(name is Either.Right)
        name as Either.Right
        assert(name.value == "George") { "Expected value for name is George, obtained value is ${name.value}" }

        val isPremium = cst.value.get<Boolean>("isPremium")
        assert(isPremium is Either.Right)
        isPremium as Either.Right
        assert(isPremium.value != null) { "Expected value for isPremium is true, obtained value is ${isPremium.value}" }
        assert(isPremium.value!!) { "Expected value for isPremium is true, obtained value is ${isPremium.value}" }

        val discountPct = cst.value.get<Double>("discountPct")
        assert(discountPct is Either.Right)
        discountPct as Either.Right
        assert(discountPct.value == 0.1) { "Expected value for discountPct is 0.1, obtained value is ${discountPct.value}" }

        val deliveryDiscountPc = cst.value.get<Float>("deliveryDiscountPc")
        assert(deliveryDiscountPc is Either.Right)
        deliveryDiscountPc as Either.Right
        assert(deliveryDiscountPc.value == null) { "Expected value for deliveryDiscountPc is null, obtained value is ${deliveryDiscountPc.value}" }

        val age = cst.value.get<Int>("age")
        assert(age is Either.Right)
        age as Either.Right
        assert(age.value == 30) { "Expected value for age is 30, obtained value is ${age.value}" }
    }

    @Test
    fun `Fail to Create BOT Instance - Key-Value Pairs - Unknown Attribute`() {
        val cst = Customer(0, State.Initial, mapOf(
            "name" to "George",
            "isPremium" to true,
            "discountPct" to 0.1,
            "deliveryDiscountPc" to null,
            "age" to 30,
            "unknown" to "value"
        ))

        assert(cst.isLeft()) { "The BOT Instance should not be created successfully" }
        cst as Either.Left

        assert(cst.value.filterIsInstance<PropertyNotFoundError>().size == 1) { "Expected 1 PropertyNotFoundError, obtained ${cst.value.filter { it is PropertyNotFoundError }.size}" }
    }
}