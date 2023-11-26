import arrow.core.raise.either
import be.vamaralds.merode.instance.EventHandler
import be.vamaralds.merode.model.*
import be.vamaralds.merode.mxp.Parser
import be.vamaralds.merode.store.MemoryBusinessObjectStore
import be.vamaralds.merode.store.MemoryEventStore
import kotlinx.coroutines.runBlocking
import kotlin.io.path.Path

fun main() {
    runBlocking {
        either<Any, Any> {
            val parser = Parser(Path("/Users/vamarald/Dev/merode/src/test/resources/model.mxp"))
            val model = parser.parseModel().bind()
            println("Model")
            println(model)

            val eventStore = MemoryEventStore()
            val objectStore = MemoryBusinessObjectStore()
            val eventHandler = EventHandler(model, eventStore, objectStore)

            val CreateCustomer = model.eventTypesByName["EVcrCustomer"]!!
            val EndCustomer = model.eventTypesByName["EVendCustomer"]!!

            val createEvent = CreateCustomer(props = mapOf(
                "email" to "john@gmail.com",
                //"premium" to null
            )).bind()

            val createEvent2 = CreateCustomer(props = mapOf(
                "email" to "vic@yahoo.fr",
                "premium" to true
            )).bind()

            val endEvent = EndCustomer(objectId = 0L).bind()

            var customer = eventHandler.handleEvent(createEvent).bind()
            eventHandler.handleEvent(createEvent2).bind()
            customer = eventHandler.handleEvent(endEvent).bind()
        }.mapLeft {
            println("An Error Occurred: $it")
        }
    }
}