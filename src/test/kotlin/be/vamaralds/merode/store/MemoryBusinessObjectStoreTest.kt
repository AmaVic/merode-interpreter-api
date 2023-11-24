package be.vamaralds.merode.store

import arrow.core.Either
import arrow.core.raise.either
import be.vamaralds.merode.model.State
import be.vamaralds.merode.model.testObjectType
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class MemoryBusinessObjectStoreTest {
    private var store = MemoryBusinessObjectStore()
    private val obj = (testObjectType()() as Either.Right).value
    private val obj2 = (testObjectType()(-1, State.Initial, mapOf(
        "name" to "XXX"
    )) as Either.Right).value

    @BeforeTest
    fun resetStore() {
        store = MemoryBusinessObjectStore()
    }

    @Test
    fun `Successfully add a new Business Object`() {
        either {
            runBlocking {
                store.addNew(obj).bind()
                val size = store.size().bind()
                assert(size == 1L) { "Expected store to contain 1 object, but it contains $size" }

                val retrieved = store.get(0).bind()
                val expected = obj.copy(id = 0)
                assert(retrieved == expected) { "Expected retrieved object to be $expected, but it is $retrieved" }
            }
        }.mapLeft { error ->
            assert(false) { "Expected to add new object, but it failed due to: $error" }
        }
    }

    @Test
    fun `Fail to Retrieve a Business Object (Does not Exist)`() {
        runBlocking {
            val contained = store.exists(0)
            assert(contained.isRight())
            contained.map { isIn ->
                assert(!isIn) { "Expected object not to be contained, but it is" }
            }
        }
    }

    @Test
    fun `Successfully Retrieve all Business Objects`() {
        either {
            runBlocking {
                val o1 = store.addNew(obj).bind()
                val o2 = store.addNew(obj).bind()

                val expectedObjects = listOf(o1, o2)
                val retrievedObjects = store.getAll().bind()
                assert(retrievedObjects == expectedObjects) { "Expected objects to be $expectedObjects, but they are $retrievedObjects" }
            }
        }.mapLeft {
            assert(false) { "Expected to retrieve all objects, but it failed due to: $it" }
        }
    }

    @Test
    fun `Successfully Update a BusinessObject`() {
        either {
            runBlocking {
                val o1 = store.addNew(obj).bind()
                val o2 = obj2.copy(id = o1.id)

                val updated = store.update(o2).bind()
                val retrieved = store.get(0).bind()
                assert(retrieved == updated) { "Expected retrieved object to be $o2, but it is $retrieved" }
                assert(o2 == updated) { "Expected updated object to be $o2, but it is $updated" }
            }
        }.mapLeft {
            assert(false) { "Expected to update object, but it failed due to: $it" }
        }
    }

    @Test
    fun `Fail to Update a BusinessObject (Does not Exist)`() {
        runBlocking {
            val updateOp = store.update(obj)
            assert(updateOp.isLeft()) { "Expected to fail to update object, but it succeeded" }
        }
    }

    @Test
    fun `Successfully Delete a BusinessObject`() {
        either {
            runBlocking {
                val o1 = store.addNew(obj).bind()
                val deleted = store.delete(o1.id).bind()
                assert(deleted == o1) { "Expected deleted object to be $o1, but it is $deleted" }

                val containsObj = store.exists(o1.id).bind()
                assert(!containsObj) { "Expected object not to be contained after delete, but it is" }
            }
        }
    }
}