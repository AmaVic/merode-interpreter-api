package be.vamaralds.merode.store

import arrow.atomic.Atomic
import arrow.core.Either
import arrow.core.right
import arrow.core.left
import be.vamaralds.merode.instance.Event

/**
 * A simple in-memory implementation of [EventStore]. It should only be used for testing or development purposes.
 */
class MemoryEventStore : EventStore {
    private val eventsByObject = mutableMapOf<Long, MutableList<Event>>()
    private val lastEventId: Atomic<Long> = Atomic(0L)

    override suspend fun append(event: Event): Either<StoreError, Event> {
        val newId = lastEventId.getAndUpdate(Long::inc)
        val newEvent = event.copy(eventId = newId)
        eventsByObject.getOrPut(event.objectId) { mutableListOf() }.add(newEvent)
        return newEvent.right()
    }

    override suspend fun getAll(): Either<StoreError, List<Event>> =
        eventsByObject.values.flatten().toList().right()

    override suspend fun get(id: Long): Either<StoreError, Event> =
        eventsByObject.values.flatten().find { it.eventId == id }?.right() ?: RecordNotFoundError("Event with id $id not found").left()


    override suspend fun getEventsForBusinessObject(id: Long): Either<StoreError, List<Event>> =
        eventsByObject[id]?.right() ?: emptyList<Event>().right()

    override suspend fun exists(id: Long): Either<StoreError, Boolean> =
        get(id).map { true.right() }.getOrNull() ?: false.right()

    override suspend fun size(): Either<StoreError, Long> =
        eventsByObject.values.flatten().size.toLong().right()
}