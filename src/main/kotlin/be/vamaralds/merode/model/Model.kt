package be.vamaralds.merode.model

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import be.vamaralds.merode.instance.BusinessObject

/**
 * Represents a Merode [Model].
 * @param objectTypes The [BusinessObjectType]s of the [Model].
 * @param eventTypes The [EventType]s of the [Model].
 */
data class Model(
    val objectTypes: Set<BusinessObjectType>,
    val existenceDependencies: Set<ExistenceDependency>
) {
    val eventTypes = objectTypes.map { it.stateMachine?.eventTypes() ?: emptyList() }.flatten().toSet()
    val eventTypesByName: Map<String, EventType> by lazy { eventTypes.associateBy { it.name } }

    /**
     * @return The [BusinessObjectType] named [name] of this [Model], if it exists
     * @return A [ModelError] if the [BusinessObjectType] does not exist in this [Model]
     */
    fun objectType(name: String): Either<ModelError, BusinessObjectType> =
        objectTypes.find { it.name == name }?.right() ?: ModelError("Business Object Type $name not found in the Model").left()

    fun masters(objectType: BusinessObjectType): Map<String, BusinessObjectType> =
        existenceDependencies
            .filter { it.dependent.name == objectType.name }
            .associate { it.name to it.master }

    fun requiredMasterRefs(eventType: EventType): Map<String, BusinessObjectType> =
        if(eventType.ownerEffect != EventType.OwnedEffect.Create)
            emptyMap()
        else
            masters(eventType.ownerType)

    fun existenceDependency(name: String): Either<ModelError, ExistenceDependency> =
        existenceDependencies.find { it.name == name }?.right() ?: ModelError("Existence Dependency with name $name is not in the model").left()
}