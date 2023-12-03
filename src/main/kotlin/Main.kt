import arrow.core.None
import arrow.core.raise.either
import be.vamaralds.merode.MerodeApplication
import be.vamaralds.merode.api.Api
import be.vamaralds.merode.common.MerodeError
import be.vamaralds.merode.instance.EventHandler
import be.vamaralds.merode.model.*
import be.vamaralds.merode.mxp.Parser
import be.vamaralds.merode.store.MemoryBusinessObjectStore
import be.vamaralds.merode.store.MemoryEventStore
import kotlinx.coroutines.runBlocking
import kotlin.io.path.Path

fun main(args: Array<String>) {
    either<MerodeError, Unit> {
        val parser = Parser(Path("/Users/vamarald/Dev/merode/src/test/resources/model2.mxp"))
        val model = parser.parseModel().bind()
        val eventStore = MemoryEventStore()
        val objectStore = MemoryBusinessObjectStore()
        val eventHandler = EventHandler(model, eventStore, objectStore)

        Api.start(eventHandler, args)
    }.mapLeft {
        MerodeApplication.logger.error { it }
    }
}