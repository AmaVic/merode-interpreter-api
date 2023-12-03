package be.vamaralds.merode.store

import arrow.core.Either
import arrow.core.EitherNel
import arrow.core.mapOrAccumulate
import arrow.core.raise.either
import be.vamaralds.merode.instance.Event
import be.vamaralds.merode.instance.BusinessObject

/**
 * A [BusinessObjectStore] is responsible for storing and retrieving [BusinessObject]s.
 */
interface BusinessObjectStore {
    /**
     * Creates a new copy of the [BusinessObject] with a new id and stores it in this [BusinessObjectStore].
     * @param obj The [BusinessObject] to store.
     * @return A copy of the [obj] with the new id if it was successfully added
     * @return A [DuplicateRecordError] if the [obj] already exists in this [EventStore] (based on [obj.id])
     * @return A [StoreError] if the [obj] could not be appended
     */
    suspend fun addNew(obj: BusinessObject): Either<StoreError, BusinessObject>

    /**
     * Updates the [BusinessObject] with the given [id] and replaces it in this [BusinessObjectStore] if it exists.
     * @param obj The [BusinessObject] to update. The [obj.id] must match the [id] of the [BusinessObject] to update.
     * @return The updated [BusinessObject] if it was successfully updated
     * @return A [RecordNotFoundError] if the [BusinessObject] does not exist in this [EventStore]
     * @return A [StoreError] if the [BusinessObject] could not be updated
     */
    suspend fun update(obj: BusinessObject): Either<StoreError, BusinessObject>

    suspend fun update(obj: Collection<BusinessObject>): EitherNel<StoreError, List<BusinessObject>> = either {
        obj.mapOrAccumulate {
            update(it).bind()
        }.bind()
    }

    /**
     * Deletes the [BusinessObject] with the given [id] from this [BusinessObjectStore] if it exists.
     * @param id The id of the [BusinessObject] to delete.
     * @return The deleted [BusinessObject] if it was successfully deleted
     * @return A [RecordNotFoundError] if the [BusinessObject] does not exist in this [EventStore]
     * @return A [StoreError] if the [BusinessObject] could not be deleted
     */
    suspend fun delete(id: Long): Either<StoreError, BusinessObject>

    /**
     * @return All [BusinessObject]s stored in this [BusinessObjectStore] if they can be successfully retrieved
     * @return A [StoreError] if the [BusinessObject]s could not be retrieved
     */
    suspend fun getAll(): Either<StoreError, List<BusinessObject>>

    /**
     * @return All [BusinessObject]s of the given [typeName] stored in this [BusinessObjectStore] if they can be successfully retrieved
     * Returns a [StoreError] if the [BusinessObject]s could not be retrieved
     */
    suspend fun getAll(typeName: String): Either<StoreError, List<BusinessObject>>

    /**
     * @return The [BusinessObject] with the given [id] if it exists and can be successfully retrieved
     * @return A [RecordNotFoundError] if the [BusinessObject] does not exist in this [EventStore]
     * @return A [StoreError] if the [BusinessObject] could not be retrieved for another reason
     */
    suspend fun get(id: Long): Either<StoreError, BusinessObject>

    /**
     * @return Whether the [BusinessObject] with the given [id] exists and can be successfully retrieved
     * @return A [StoreError] if the store fails to verify the existence of the [BusinessObject]
     */
    suspend fun exists(id: Long): Either<StoreError, Boolean>

    /**
     * @return The number of [BusinessObject]s stored in this [BusinessObjectStore] if they can be successfully counted
     * @return A [StoreError] if the [BusinessObject]s could not be counted
     */
    suspend fun size(): Either<StoreError, Long>
}