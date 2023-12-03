package be.vamaralds.merode.store

import arrow.core.Either
import arrow.core.raise.either
import be.vamaralds.merode.model.customerType
import be.vamaralds.merode.model.testModel
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class MemoryEventStoreTest {
    private var eventStore = MemoryEventStore()
    private val objType = customerType()


    private val event = with(testModel()) {
        (customerType().stateMachine!!.eventTypes().first()(
            0, 0, mapOf(
                "name" to "George"
            )
        ) as Either.Right).value
    }

    @BeforeTest
    fun resetEventStore() {
        eventStore = MemoryEventStore()
    }

    @Test
    fun `Successfully Storing and Retrieving an Event`() {
        runBlocking {
            val result = eventStore.append(event)
            assert(result.isRight()) { "Expected event to be stored, but it failed due to: ${(result as Either.Left).value}" }

            val contained = eventStore.exists(0)
            assert(contained.isRight())

            contained.map { isIn ->
                assert(isIn) { "Expected event to be contained, but it is not" }
            }
        }
    }

    @Test
    fun `Fail to Retrieve an Event (Does not Exist)`() {
        runBlocking {
            val contained = eventStore.exists(0)
            assert(contained.isRight())
            contained.map { isIn ->
                assert(!isIn) { "Expected event not to be contained, but it is" }
            }
        }
    }

    @Test
    fun `Successfully Retrieve all Events`() {
        runBlocking {
            eventStore.append(event)
            eventStore.append(event)

            val sizeOp = eventStore.size()
            assert(sizeOp.isRight()) { "Expected to get size of event store, but it failed due to: ${(sizeOp as Either.Left).value}" }
            sizeOp as Either.Right

            assert(sizeOp.value == 2L) { "Expected event store to contain 2 events, but it contains ${sizeOp.value}" }
        }
    }

    @Test
    fun `Successfully Retrieve all Events for a Business Object`() {
        either {
            runBlocking {
                val ev1 = eventStore.append(event).bind()
                val ev2 = eventStore.append(event).bind()

                val evtsForBO = eventStore.getEventsForBusinessObject(0).bind()
                assert(ev1 in evtsForBO) { "Expected event $ev1 to be contained in events for BO, but it is not" }
                assert(ev2 in evtsForBO) { "Expected event $ev2 to be contained in events for BO, but it is not" } }
        }.mapLeft {
            assert(false) { "Expected to retrieve events for BO, but it failed due to: $it" }
        }
    }

    @Test
    fun `Retrieve Empty Events for a Business Object (Not Found)`() {
        runBlocking {
            val evtsForBO = eventStore.getEventsForBusinessObject(0)
            assert(evtsForBO.isRight()) { "Expected to fail to retrieve events for BO, but it succeeded" }
            evtsForBO as Either.Right

            assert(evtsForBO.value.isEmpty()) { "Expected to retrieve empty list of events for BO, but it is not empty" }
        }
    }
}