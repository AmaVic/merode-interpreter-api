package be.vamaralds.merode.model

/**
 * Represents an [Attribute] of a [BusinessObjectType].
 * @param name The name of the [Attribute].
 * @param type The [Type] of the [Attribute].
 */
data class Attribute(val name: String, val type: Type) {
    /**
     * Represents the [Type] of value an [Attribute] has at runtime.
     */
    enum class Type {
        String,
        Int,
        Float,
        Double,
        Boolean
    }
}