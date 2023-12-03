package be.vamaralds.merode.api

import arrow.core.raise.either
import be.vamaralds.merode.MerodeApplication
import be.vamaralds.merode.common.MerodeError
import be.vamaralds.merode.instance.BusinessObject
import be.vamaralds.merode.instance.Event
import be.vamaralds.merode.instance.EventHandler
import be.vamaralds.merode.mxp.Parser
import be.vamaralds.merode.serialization.safe
import be.vamaralds.merode.store.MemoryBusinessObjectStore
import be.vamaralds.merode.store.MemoryEventStore
import kotlin.test.Test
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread
import kotlin.io.path.Path

class ApiTest {
    private val client = HttpClient(CIO)

    init {
        either<MerodeError, Unit> {
            val modelFilePath = this.javaClass.classLoader.getResource("model.mxp")!!.path
            val parser = Parser(Path(modelFilePath))
            val model = parser.parseModel().bind()
            val eventStore = MemoryEventStore()
            val objectStore = MemoryBusinessObjectStore()
            val eventHandler = EventHandler(model, eventStore, objectStore)
            Api.eventHandler = eventHandler
        }.mapLeft {
            throw it
        }
    }

    @Test
    fun `Successfully Submit a new Business Event`() = testApplication {
        runBlocking {
            val eventJson = """
            {
                "type": "EVcrCustomer",
                "properties": {
                    "email": "vic@gmail.com",
                    "premium": false
                }
            }
        """.trimIndent()

            val response = client.post("/event") {
                contentType(ContentType.Application.Json)
                setBody(eventJson)
            }

            assert(response.status == HttpStatusCode.OK)
        }
    }

    @Test
    fun `Successfully Retrieve Empty Business Events List`() = testApplication {
        runBlocking {
            val response = client.get("/event")
            assert(response.status == HttpStatusCode.OK) { "Status code is ${response.status} but it was expected to be ${HttpStatusCode.OK}" }
            val responseJsonString = response.bodyAsText()
            val eventsJsonArray: JSONArray = JSONArray(responseJsonString)
            val events = eventsJsonArray.map { Event::fromJsonString }
            assert(events.isEmpty())
        }
    }

    @Test
    fun `Successfully Retrieve All Business Events`() = testApplication {
        with(Api.eventHandler!!.model) {
            runBlocking {
                either {
                    //Add Events
                    val event1Json = """
            {
                "type": "EVcrCustomer",
                "properties": {
                    "email": "george@gmail.com"
                }
            }
            """.trimIndent()
                    val event1 = Event.fromJsonString(event1Json).bind()
                    Api.eventHandler!!.eventStore.append(event1).bind()
                    val expectedEvent = Api.eventHandler!!.eventStore.getAll().bind().first()
                    val response = client.get("/event")
                    assert(response.status == HttpStatusCode.OK) { "Status code is ${response.status} but it was expected to be ${HttpStatusCode.OK}" }
                    val responseJsonString = response.bodyAsText()
                    MerodeApplication.logger.info { responseJsonString }
                    val eventsJsonArray = JSONArray(responseJsonString)
                    val events = eventsJsonArray.map { Event.fromJsonString(it.toString()).bind() }
                    assert(events.size == 1) { "There should be 1 event, but there are ${events.size}" }
                    assert(events[0] == expectedEvent) { "The event retrieved (${events[0]}) is not the same as the one added ($expectedEvent)" }
                }
            }.mapLeft {
                assert(false) { "Success was expected, but it failed due to: $it" }
            }
        }
    }

    @Test
    fun `Fail to Submit a Business Event (Type Missing)`() = testApplication {
        runBlocking {
            val eventJson = """
            {
                "x": "EVcrCustomer",
                "properties": {
                    "email": "vic@gmail.com",
                    "premium": false
                }
            }
        """.trimIndent()

            val response = client.post("/event") {
                contentType(ContentType.Application.Json)
                setBody(eventJson)
            }

            assert(response.status == HttpStatusCode.BadRequest)
        }
    }

    @Test
    fun `Successfully Retrieve a List of Business Objects`() = testApplication {
        with(Api.eventHandler!!.model) {
            runBlocking {
                either {
                    val eventJson = """
            {
                "type": "EVcrCustomer",
                "properties": {
                    "email": "george@gmail.com"
                 }
            }"""
                    val event = Event.fromJsonString(eventJson).bind()
                    val obj = Api.eventHandler!!.handleEvent(event).bind().first()

                    val response = client.get("/customer")
                    assert(response.status == HttpStatusCode.OK) { "Status code is ${response.status} but it was expected to be ${HttpStatusCode.OK}" }
                    val responseJsonString = response.bodyAsText()
                    val objectsJsonArray = JSONArray(responseJsonString)
                    val retrievedObjects = objectsJsonArray.map {
                        val objJson = JSONObject(it.toString())
                        with(Api.eventHandler!!.model) { BusinessObject.fromJsonString(objJson.toString()).bind() }
                    }
                    assert(retrievedObjects.size == 1) { "There should be 1 object, but there are ${retrievedObjects.size}" }
                    assert(retrievedObjects[0] == obj) { "The object retrieved (${retrievedObjects[0]}) is not the same as the one added ($obj)" }
                }
            }
        }
    }

    @Test
    fun `Successfully Retrieve a Specific Business Objects`() = testApplication {
        with(Api.eventHandler!!.model) {
            runBlocking {
                either {
                    val eventJson = """
            {
                "type": "EVcrCustomer",
                "properties": {
                    "email": "george@gmail.com"
                 }
            }"""
                    val event = Event.fromJsonString(eventJson).bind()
                    val obj = Api.eventHandler!!.handleEvent(event).bind().first()

                    val response = client.get("/customer/0")
                    assert(response.status == HttpStatusCode.OK) { "Status code is ${response.status} but it was expected to be ${HttpStatusCode.OK}" }
                    val responseJsonString = response.bodyAsText()
                    val objJson = JSONObject(responseJsonString)
                    with(Api.eventHandler!!.model) {
                        val retrievedObj = BusinessObject.fromJsonString(objJson.toString()).bind()
                        assert(retrievedObj == obj) { "The object retrieved ($retrievedObj) is not the same as the one added ($obj)" }
                    }
                }
            }
        }
    }
}