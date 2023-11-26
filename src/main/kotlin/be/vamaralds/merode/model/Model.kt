package be.vamaralds.merode.model

import arrow.core.Either
import arrow.core.left
import arrow.core.right

/**
 * Represents a Merode [Model].
 * @param name The name of the [Model].
 * @param objectTypes The [BusinessObjectType]s of the [Model].
 * @param eventTypes The [EventType]s of the [Model].
 */
data class Model(
    val name: String,
    val objectTypes: Set<BusinessObjectType>,
) {
    val eventTypes = objectTypes.map { it.stateMachine?.eventTypes() ?: emptyList() }.flatten().toSet()

    /**
     * @return The [BusinessObjectType] named [name] of this [Model], if it exists
     * @return A [ModelError] if the [BusinessObjectType] does not exist in this [Model]
     */
    fun objectType(name: String): Either<ModelError, BusinessObjectType> =
        objectTypes.find { it.name == name }?.right() ?: ModelError("Business Object Type $name not found in the Model").left()
}