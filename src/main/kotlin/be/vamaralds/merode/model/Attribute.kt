package be.vamaralds.merode.model

data class Attribute(val name: String, val type: Type) {
    enum class Type {
        String,
        Int,
        Float,
        Double,
        Boolean
    }
}