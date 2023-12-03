package be.vamaralds.merode.model

import arrow.core.Either
import be.vamaralds.merode.instance.PropertyNotFoundError
import kotlin.test.Test

class EventTypeTest {
    private val Customer = customerType()

    private val CreateCustomerEvent = Customer.stateMachine!!.eventTypes().find { it.name == "CreateCustomer"}!!

    val testModel = testModel()
    @Test
    fun `Successfully Retrieve EventType Attribute`() {
        val attributeRetrieval = CreateCustomerEvent.attribute("name")
        assert(attributeRetrieval.isRight())
    }

    @Test
    fun `Fail to retrieve EventType Attribute`() {
        val attributeRetrieval = CreateCustomerEvent.attribute("unknown")
        assert(attributeRetrieval.isLeft())
    }

    @Test
    fun `Successfully Create Event Instance - All Attributes`() {
        with(testModel) {
            val ev = CreateCustomerEvent(
                0, 0, mapOf(
                    "name" to "George",
                    "isPremium" to true,
                    "discountPct" to 0.1,
                    "deliveryDiscountPc" to null,
                    "age" to 30
                )
            )

            assert(ev.isRight())
            ev as Either.Right

            assert(ev.value.eventId == 0L) { "Expected value for id is 0, obtained value is ${ev.value.eventId}" }

            val name = ev.value.get<String>("name")
            assert(name is Either.Right)
            name as Either.Right
            assert(name.value == "George") { "Expected value for name is George, obtained value is ${name.value}" }

            val isPremium = ev.value.get<Boolean>("isPremium")
            assert(isPremium is Either.Right)
            isPremium as Either.Right
            assert(isPremium.value != null) { "Expected value for isPremium is true, obtained value is ${isPremium.value}" }
            assert(isPremium.value!!) { "Expected value for isPremium is true, obtained value is ${isPremium.value}" }

            val discountPct = ev.value.get<Double>("discountPct")
            assert(discountPct is Either.Right)
            discountPct as Either.Right
            assert(discountPct.value == 0.1) { "Expected value for discountPct is 0.1, obtained value is ${discountPct.value}" }

            /*
        val deliveryDiscountPc = ev.value.get<Float>("deliveryDiscountPc")
        assert(deliveryDiscountPc.isRight()) { "Expected to get value of deliveryDiscountPc, but it failed due to: ${(deliveryDiscountPc as Either.Left).value}" }

        deliveryDiscountPc as Either.Right
        assert(deliveryDiscountPc.value == null) { "Expected value for deliveryDiscountPc is null, obtained value is ${deliveryDiscountPc.value}" }

         */

            val age = ev.value.get<Int>("age")
            assert(age is Either.Right)
            age as Either.Right
            assert(age.value == 30) { "Expected value for age is 30, obtained value is ${age.value}" }
        }
    }

    /*@Test
    fun `Successfully Create Event Instance - With Missing Attributes`() {
        val ev = CreateCustomerEvent(0, 0, mapOf(
            "name" to "George",
            "isPremium" to true,
            "discountPct" to 0.1,
            //"deliveryDiscountPc" to null,
            //"age" to 30
        ))

        assert(ev.isRight())
        ev as Either.Right

        assert(ev.value.eventId == 0L) { "Expected value for id is 0, obtained value is ${ev.value.eventId}" }

        val name = ev.value.get<String>("name")
        assert(name is Either.Right)
        name as Either.Right
        assert(name.value == "George") { "Expected value for name is George, obtained value is ${name.value}" }

        val isPremium = ev.value.get<Boolean>("isPremium")
        assert(isPremium is Either.Right)
        isPremium as Either.Right
        assert(isPremium.value != null) { "Expected value for isPremium is true, obtained value is ${isPremium.value}" }
        assert(isPremium.value!!) { "Expected value for isPremium is true, obtained value is ${isPremium.value}" }

        val discountPct = ev.value.get<Double>("discountPct")
        assert(discountPct is Either.Right)
        discountPct as Either.Right
        assert(discountPct.value == 0.1) { "Expected value for discountPct is 0.1, obtained value is ${discountPct.value}" }

        val deliveryDiscountPc = ev.value.get<Float>("deliveryDiscountPc")
        assert(deliveryDiscountPc is Either.Right)
        deliveryDiscountPc as Either.Right
        assert(deliveryDiscountPc.value == null) { "Expected value for deliveryDiscountPc is null, obtained value is ${deliveryDiscountPc.value}" }

        val age = ev.value.get<Int>("age")
        assert(age is Either.Right)
        age as Either.Right
        assert(age.value == null) { "Expected value for age is null, obtained value is ${deliveryDiscountPc.value}" }
    }*/

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