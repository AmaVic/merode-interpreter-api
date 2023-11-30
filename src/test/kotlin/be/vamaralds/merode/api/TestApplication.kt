package be.vamaralds.merode.api

import io.ktor.server.testing.*

fun testModule() = testApplication {
    application {
        configureRouting()
        configureLogging()
    }
}