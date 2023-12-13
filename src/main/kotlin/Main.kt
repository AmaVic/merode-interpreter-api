import arrow.core.None
import arrow.core.raise.either
import be.vamaralds.merode.MerodeApplication
import be.vamaralds.merode.api.Api
import be.vamaralds.merode.cli.StartCommand
import be.vamaralds.merode.common.MerodeError
import be.vamaralds.merode.instance.EventHandler
import be.vamaralds.merode.model.*
import be.vamaralds.merode.mxp.Parser
import be.vamaralds.merode.store.MemoryBusinessObjectStore
import be.vamaralds.merode.store.MemoryEventStore
import kotlinx.coroutines.runBlocking
import kotlin.io.path.Path

suspend fun main(args: Array<String>) {
    StartCommand(args).main(args)
}