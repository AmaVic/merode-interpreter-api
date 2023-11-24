package be.vamaralds.merode.instance

import arrow.core.Either
import arrow.core.None
import arrow.core.raise.either
import be.vamaralds.merode.common.MerodeError
import be.vamaralds.merode.model.*
import kotlin.test.Test

class BusinessObjectTest {
    private val Customer: BusinessObjectType = testObjectType()

    @Test
    fun `Successfully get BO Attribute`() {
        val cst = Customer(0, State.Initial, "George", null, null, null, null, null)
        assert(cst.isRight()) { "Expected Customer to be created, but it failed due to: ${(cst as Either.Left).value}" }
        cst as Either.Right

        val cstName = cst.value.get<String>("name")
        assert(cstName is Either.Right) { "Expected Customer name to be retrieved, but it failed due to: ${(cstName as Either.Left).value}" }
        cstName as Either.Right
        assert(cstName.value == "George") { "Expected Customer name to be George, but it is ${cstName.value}" }
    }

    @Test
    fun `Fail to get BO Attribute (Unknown)`() {
        val cst = Customer(0, State.Initial, "George", null, null, null, null, null)
        assert(cst.isRight()) { "Expected Customer to be created, but it failed due to: ${(cst as Either.Left).value}" }
        cst as Either.Right

        val unknownAttributeVal = cst.value.get<String>("unknown")
        assert(unknownAttributeVal is Either.Left) { "Expected Customer unknown attribute to fail, but it succeeded" }
    }

    @Test
    fun `Successfully handle an Event`() {
        val cst = Customer()
        assert(cst.isRight())
        val obj = (cst as Either.Right).value.copy(id = 0)

        val event = Customer.stateMachine!!.eventTypes().first()(0, 0, mapOf(
            "name" to "George",
            "isPremium" to true,
            "discountPct" to 0.1,
            "deliveryDiscountPc" to null,
            "age" to 30
        ))
        assert(event.isRight())
        event as Either.Right

        val cst2 = obj.handleEvent(event.value)
        assert(cst2.isRight()) { "Expected Customer to be updated, but it failed due to: ${(cst2 as Either.Left).value}" }
        cst2 as Either.Right

        val id = cst2.value.id
        assert(id == 0L) { "Expected Customer id to be 0, but it is $id" }

        val state = cst2.value.state
        assert(state.name == "Created") { "Expected Customer state to be Created, but it is ${state.name}" }


        val name = (cst2.value.get<String>("name") as Either.Right).value
        assert(name == "George") { "Expected Customer name to be George, but it is $name" }

        val isPremium = (cst2.value.get<Boolean>("isPremium") as Either.Right).value
        assert(isPremium != null) { "Expected Customer isPremium to be true, but it is $isPremium" }
        assert(isPremium!!) { "Expected Customer isPremium to be true, but it is $isPremium" }

        val discountPct = (cst2.value.get<Double>("discountPct") as Either.Right).value
        assert(discountPct == 0.1) { "Expected Customer discountPct to be 0.1, but it is $discountPct" }

        val deliveryDiscountPc = (cst2.value.get<Float>("deliveryDiscountPc") as Either.Right).value
        assert(deliveryDiscountPc == null) { "Expected Customer deliveryDiscountPc to be null, but it is $deliveryDiscountPc" }

        val age = (cst2.value.get<Int>("age") as Either.Right).value
        assert(age == 30) { "Expected Customer age to be 30, but it is $age" }

        val banReason = (cst2.value.get<String>("banReason") as Either.Right).value
        assert(banReason == null) { "Expected Customer banReason to be null, but it is $banReason" }
    }

    @Test
    fun `Fail to Handle Event (Invalid Transition)`() {
        val cst = Customer()
        assert(cst.isRight())
        cst as Either.Right

        val event = Customer.stateMachine!!.eventTypes()[1](0, 0)
        assert(event.isRight())
        event as Either.Right

        val cst2 = cst.value.handleEvent(event.value)
        assert(cst2.isLeft()) { "Expected Customer to fail to handle event, but it succeeded" }
    }

    @Test
    fun `Fail to Handle Event (Invalid Property)`() {
        val cst = Customer()
        assert(cst.isRight())
        cst as Either.Right

        val event = Customer.stateMachine!!.eventTypes()[1](0, 0, mapOf(
            "x" to "George"
        ))

        assert(event.isLeft()) { "Expected Customer to fail to handle event, but it succeeded" }
    }

    @Test
    fun `Fail to Handle Event (Invalid Target Object)`() {
        val cst = Customer()
        assert(cst.isRight())
        cst as Either.Right

        val event = Customer.stateMachine!!.eventTypes()[1](0, 10)
        assert(event.isRight())
        event as Either.Right

        val cst2 = cst.value.handleEvent(event.value)
        assert(cst2.isLeft()) { "Expected Customer to fail to handle event, but it succeeded" }
    }
}