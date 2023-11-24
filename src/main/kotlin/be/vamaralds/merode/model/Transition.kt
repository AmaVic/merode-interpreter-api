package be.vamaralds.merode.model


/**
 * Represents a [Transition] in a [StateMachine].
 * @param onEventType The [EventType] that triggers the [Transition].
 * @param fromState The [State] from which the [Transition] starts.
 * @param toState The [State] to which the [Transition] leads.
 */
data class Transition(val onEventType: EventType, val fromState: State, val toState: State)