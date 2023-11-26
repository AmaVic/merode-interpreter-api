package be.vamaralds.merode.instance

import arrow.core.Either
import arrow.core.nel
import arrow.core.raise.either
import be.vamaralds.merode.model.State
import be.vamaralds.merode.model.testModel
import be.vamaralds.merode.model.testObjectType
import be.vamaralds.merode.store.MemoryBusinessObjectStore
import be.vamaralds.merode.store.MemoryEventStore
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class EventHandlerTest {
    private var eventStore = MemoryEventStore()
    private var objectStore = MemoryBusinessObjectStore()
    private val model = testModel()
    private var eventHandler = EventHandler(model, eventStore, objectStore)
    private val Customer = (model.objectType("Customer") as Either.Right).value
    private val CreateCustomerEvent = Customer.stateMachine!!.eventTypes().find { it.name == "CreateCustomer" }!!
    private val UpdateCustomerNameEvent = Customer.stateMachine!!.eventTypes().find { it.name == "UpdateCustomerName" }!!
    private val BanCustomerEvent = Customer.stateMachine!!.eventTypes().find { it.name == "BanCustomer" }!!

    @BeforeTest
    fun resetHandler() {
        eventStore = MemoryEventStore()
        objectStore = MemoryBusinessObjectStore()
        eventHandler = EventHandler(model, eventStore, objectStore)
    }

    @Test
    fun `Successfully Handle a Create Event`() {
        either {
            runBlocking {
                val event = CreateCustomerEvent(-1, -1, mapOf("name" to "George")).bind()
                val expectedStoredEvent = CreateCustomerEvent(0, 0, mapOf("name" to "George")).bind()
                val expectedStoredObject = Customer(0, State("Created", State.Type.Living), mapOf("name" to "George")).bind()
                val expectedAffectedObjects = listOf(expectedStoredObject)

                val affectedObjects = eventHandler.handleEvent(event).bind()

                val retrievedEvent = eventStore.get(0).mapLeft {
                    InstanceError("Could not retrieve event with id 0 from event store").nel()
                }.bind()

                assert(retrievedEvent == expectedStoredEvent) { "Stored event ($retrievedEvent) does not match expected event ($expectedStoredEvent)" }
                assert(expectedAffectedObjects == affectedObjects) { "Affected objects do not match expected objects" }
            }
        }.mapLeft {
            it.forEach { it.printStackTrace() }
            assert(false) { "Event handling failed: $it, but was expected to be successful" }
        }
    }

    @Test
    fun `Successfully Handle a Create Event`() {
        either {
            runBlocking {
                val event = CreateCustomerEvent(-1, -1, mapOf("name" to "George")).bind()
                val expectedStoredEvent = CreateCustomerEvent(0, 0, mapOf("name" to "George")).bind()
                val expectedStoredObject = Customer(0, State("Created", State.Type.Living), mapOf("name" to "George")).bind()
                val expectedAffectedObjects = listOf(expectedStoredObject)

                val affectedObjects = eventHandler.handleEvent(event).bind()

                val retrievedEvent = eventStore.get(0).mapLeft {
                    InstanceError("Could not retrieve event with id 0 from event store").nel()
                }.bind()

                assert(retrievedEvent == expectedStoredEvent) { "Stored event ($retrievedEvent) does not match expected event ($expectedStoredEvent)" }
                assert(expectedAffectedObjects == affectedObjects) { "Affected objects do not match expected objects" }
            }
        }.mapLeft {
            it.forEach { it.printStackTrace() }
            assert(false) { "Event handling failed: $it, but was expected to be successful" }
        }
    }
}