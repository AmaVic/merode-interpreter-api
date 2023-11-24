package be.vamaralds.merode.model

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import be.vamaralds.merode.instance.EventHandlingError
import be.vamaralds.merode.instance.BusinessObject

/**
 * Represents a [StateMachine] that defines the lifecycle of a [BusinessObjectType].
 * A state machine is defined by a set of [State]s and [Transition]s between them.
 * Using the [nextState] method, the next [State] of the [StateMachine] can be retrieved for a given [State] and [EventType].
 * This is the way [BusinessObject]s evolve through their lifecycle.
 */
data class StateMachine(private val matrix: Map<State, Map<EventType, State>> = emptyMap()) {
    companion object {
        /**
         * @return A new [StateMachine] from the provided [transitions]. The [StateMachine] will contain all the [State]s and [EventType]s of the provided [transitions].
         */
        operator fun invoke(transitions: Set<Transition>): StateMachine {
            val matrix: MutableMap<State, MutableMap<EventType, State>> = mutableMapOf()
            val fromStates: Set<State> = transitions.map { it.fromState }.toSet()

            fromStates.forEach { state ->
                val outgoingTransitions = transitions.filter { it.fromState == state }
                val trsMatrix = outgoingTransitions.map { it.onEventType to it.toState }.toMap()
                matrix[state] = trsMatrix.toMutableMap()
            }

            return StateMachine(matrix)
        }
    }

    /**
     * @return If there is a [Transition] from [currentState] on [event], returns the [State] to which the [Transition] leads ([Transition.toState]).
     * @return Otherwise, returns an [EventHandlingError].
     */
    fun nextState(currentState: State, event: EventType): Either<EventHandlingError, State> =
        matrix[currentState]?.get(event)?.right() ?: EventHandlingError("No transition found for event ${event.name} from state ${currentState.name}").left()

    /**
     * @return A list of all the [EventType]s that are used in this [StateMachine].
     */
    fun eventTypes(): List<EventType> = matrix.values.flatMap { it.keys }.distinct()
}