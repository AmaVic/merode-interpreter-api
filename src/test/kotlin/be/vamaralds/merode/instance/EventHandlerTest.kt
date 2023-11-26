package be.vamaralds.merode.instance

import arrow.core.Either
import arrow.core.nel
import arrow.core.raise.either
import be.vamaralds.merode.model.State
import be.vamaralds.merode.model.testModel
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
    fun `Successfully Handle a Modify Event`() {
        either {
            runBlocking {
                val createEvent = CreateCustomerEvent(-1, -1, mapOf("name" to "George")).bind()
                eventHandler.handleEvent(createEvent).bind()
                val event = UpdateCustomerNameEvent(0, 0, mapOf("name" to "XYZ")).bind()
                val updatedObj = eventHandler.handleEvent(event).bind()
                assert(updatedObj.first().get<String>("name").bind() == "XYZ") { "Object was not updated correctly" }
            }
        }.mapLeft {
            assert(false) { "Event handling failed due to $it, but was expected to be successful" }
        }
    }

    @Test
    fun `Successfully Handle an End Event`() {
        either {
            runBlocking {
                val createEvent = CreateCustomerEvent(-1, -1, mapOf("name" to "George")).bind()
                eventHandler.handleEvent(createEvent).bind()
                val event = BanCustomerEvent(0, 0, mapOf("banReason" to "Fraud")).bind()
                val updatedObj = eventHandler.handleEvent(event).bind()
                assert(updatedObj.first().get<String>("banReason").bind() == "Fraud") { "Object was not updated correctly" }
            }
        }.mapLeft {
            assert(false) { "Event handling failed due to $it, but was expected to be successful" }
        }
    }

    @Test
    fun `Fail to Handle an Event (Invalid Property)`() {
        either {
            runBlocking {
                val createEvent = CreateCustomerEvent(-1, -1, mapOf("name" to true)).bind()
                eventHandler.handleEvent(createEvent).bind()
                assert(false) { "Event handling succeeded, but was expected to fail" }
            }
        }
    }

    @Test
    fun `Fail to Handle an Event (Object Not Found)`() {
        either {
            runBlocking {
                val event = UpdateCustomerNameEvent(-1, -1, mapOf("name" to "George")).bind()
                eventHandler.handleEvent(event).bind()
                assert(false) { "Event handling succeeded, but was expected to fail" }
            }
        }
    }

    @Test
    fun `Fail to Handle an Event (Invalid Transition)`() {
        either {
            runBlocking {
                val createEvent = CreateCustomerEvent(-1, -1, mapOf("name" to "George")).bind()
                val banEvent = BanCustomerEvent(-1, 0, mapOf("banReason" to "Fraud")).bind()
                val updateEvent = UpdateCustomerNameEvent(-1, 0, mapOf("name" to "XYZ")).bind()

                eventHandler.handleEvent(createEvent).bind()
                eventHandler.handleEvent(banEvent).bind()
                val lst = eventHandler.handleEvent(updateEvent).bind()
                println(lst)
                assert(false) { "Event handling succeeded, but was expected to fail" }
            }
        }
    }
}