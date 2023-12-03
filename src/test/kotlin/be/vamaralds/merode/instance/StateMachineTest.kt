package be.vamaralds.merode.instance

import arrow.core.Either
import be.vamaralds.merode.model.State
import be.vamaralds.merode.model.customerType
import kotlin.test.Test

class StateMachineTest {
    private val Customer = customerType()
    private val stateMachine = Customer.stateMachine!!

    @Test
    fun `Successful Event Handling`() {
        val eventType = Customer.stateMachine!!.eventTypes().find { it.name == "CreateCustomer" }!!
        val eventHandling = stateMachine.nextState(State.Initial, eventType)
        assert(eventHandling.isRight()) { "Expected event handling to succeed, but it failed due to: ${(eventHandling as Either.Left).value}" }
        eventHandling as Either.Right
        val newState = eventHandling.value
        assert(newState.name == "Created")
    }
}