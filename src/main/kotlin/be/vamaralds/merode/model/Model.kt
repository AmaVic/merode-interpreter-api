package be.vamaralds.merode.model

/**
 * Represents a Merode [Model].
 * @param name The name of the [Model].
 * @param objectTypes The [BusinessObjectType]s of the [Model].
 * @param eventTypes The [EventType]s of the [Model].
 */
data class Model(
    val name: String,
    val objectTypes: Set<BusinessObjectType>,
    val eventTypes: Set<EventType>,
)