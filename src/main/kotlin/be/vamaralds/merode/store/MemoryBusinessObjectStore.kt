package be.vamaralds.merode.store

import arrow.atomic.Atomic
import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import be.vamaralds.merode.instance.BusinessObject

/**
 * Simple, In-Memory implementation of [BusinessObjectStore]. It should only be used for testing or development purposes.
 */
class MemoryBusinessObjectStore: BusinessObjectStore {
    private val objectsById = mutableMapOf<Long, BusinessObject>()
    private val lastObjectId: Atomic<Long> = Atomic(0L)

    override suspend fun addNew(obj: BusinessObject): Either<StoreError, BusinessObject> = either {
        val newId = lastObjectId.getAndUpdate(Long::inc)
        val newObj = obj.copy(id = newId)
        objectsById[newObj.id] = newObj
        newObj
    }

    override suspend fun update(obj: BusinessObject): Either<StoreError, BusinessObject> = either {
        ensure(exists(obj.id).bind()) { RecordNotFoundError("Object with id ${obj.id} does not exist") }
        objectsById[obj.id] = obj
        obj
    }

    override suspend fun delete(id: Long): Either<StoreError, BusinessObject> = either {
        ensure(exists(id).bind()) { RecordNotFoundError("Object with id $id does not exist") }
        objectsById.remove(id)!!
    }

    override suspend fun getAll(): Either<StoreError, List<BusinessObject>> =
        objectsById.values.toList().right()

    override suspend fun get(id: Long): Either<StoreError, BusinessObject> = either {
        ensure(exists(id).bind()) { RecordNotFoundError("Object with id $id does not exist") }
        objectsById[id]!!
    }

    override suspend fun exists(id: Long): Either<StoreError, Boolean> =
        objectsById.containsKey(id).right()

    override suspend fun size(): Either<StoreError, Long> =
        objectsById.size.toLong().right()

}