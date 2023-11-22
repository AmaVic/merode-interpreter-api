package be.vamaralds.merode.model

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import be.vamaralds.merode.instance.EventHandlingError

data class StateMachine(private val matrix: Map<State, Map<EventType, State>> = emptyMap()) {
    companion object {
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

    fun nextState(currentState: State, event: EventType): Either<EventHandlingError, State> =
        matrix[currentState]?.get(event)?.right() ?: EventHandlingError("No transition found for event ${event.name} from state ${currentState.name}").left()

    fun eventTypes(): List<EventType> = matrix.values.flatMap { it.keys }.distinct()
}