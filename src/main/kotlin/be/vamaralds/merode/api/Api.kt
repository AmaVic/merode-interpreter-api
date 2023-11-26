package be.vamaralds.merode.api

import be.vamaralds.merode.instance.EventHandler
import io.ktor.server.application.*
import be.vamaralds.merode.model.Model

object Api {
    var eventHandler: EventHandler? = null
        private set

    fun start(eventHandler: EventHandler, args: Array<String>) {
        Api.eventHandler = eventHandler
        io.ktor.server.netty.EngineMain.main(args)
    }
}

fun Application.module() {
    configureRouting()
    configureLogging()
}