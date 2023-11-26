package be.vamaralds.merode.model

import be.vamaralds.merode.instance.BusinessObject

/**
 * Represents a [State] in a [StateMachine].
 * @param name The name of the [State].
 * @param type The [Type] of the [State].
 */
data class State(val name: String, val type: Type) {
    companion object {
        /**
         * When a [BusinessObject] is created for the first time, before applying any event to it, it is in an [Initial] state.
         * This [State] is always present in a [StateMachine] and is the same for any [BusinessObjectType].
         */
        val Initial = State("initial", Type.Initial)
    }

    enum class Type {
        Initial,
        Living,
        Final
    }
}