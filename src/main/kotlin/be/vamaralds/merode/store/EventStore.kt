package be.vamaralds.merode.store

import arrow.core.Either
import arrow.core.None
import be.vamaralds.merode.instance.Event

/**
 * An [EventStore] is responsible for storing and retrieving [Event]s.
 */
interface EventStore {
    /**
     * Assigns a new id to the [Event] and stores it in this [EventStore].
     * @param event The [Event] to store.
     * @return The [Event] with the new id if it was successfully appended
     * @return A [StoreError] if the [Event] could not be appended
     */
    suspend fun append(event: Event): Either<StoreError, Event>

    /**
     * @return All [Event]s stored in this [EventStore] if they can be successfully retrieved
     * @return A [StoreError] if the [Event]s could not be retrieved
     */
    suspend fun getAll(): Either<StoreError, List<Event>>

    /**
     * @return The [Event] with the given [id] if it exists and can be successfully retrieved
     * @return A [RecordNotFoundError] if the [Event] does not exist in this [EventStore]
     * @return A [StoreError] if the [Event] could not be retrieved
     */
    suspend fun get(id: Long): Either<StoreError, Event>

    /**
     * @return All [Event]s for the [BusinessObject] with the given [id] if they exist and can be successfully retrieved
     * @return A [StoreError] if the [Event]s could not be retrieved
     */
    suspend fun getEventsForBusinessObject(id: Long): Either<StoreError, List<Event>>

    /**
     * @return Whether the [Event] with the given [id] exists and can be successfully retrieved
     * @return A [StoreError] if the store fails to verify the existence of the [Event]
     */
    suspend fun exists(id: Long): Either<StoreError, Boolean>

    /**
     * @return The number of [Event]s stored in this [EventStore] if they can be successfully counted
     * @return A [StoreError] if the [Event]s could not be counted
     */
    suspend fun size(): Either<StoreError, Long>
}