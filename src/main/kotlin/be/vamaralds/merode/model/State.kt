package be.vamaralds.merode.model

data class State(val name: String) {
    companion object {
        val Initial = State("Initial")
    }
}