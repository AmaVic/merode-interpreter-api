package be.vamaralds.merode

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import be.vamaralds.merode.api.Api
import be.vamaralds.merode.common.MerodeError
import be.vamaralds.merode.instance.EventHandler
import be.vamaralds.merode.mxp.Parser
import be.vamaralds.merode.store.MemoryBusinessObjectStore
import be.vamaralds.merode.store.MemoryEventStore
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.server.application.*
import java.nio.file.Path
import kotlin.io.path.Path

object MerodeApplication {
    val logger = KotlinLogging.logger { }

    suspend fun start(args: Array<String>) = either {
        val modelFilePath = modelFilePath().bind()
        val parser = Parser(modelFilePath)
        val model = parser.parseModel().bind()
        val eventStore = MemoryEventStore()
        val objectStore = MemoryBusinessObjectStore()
        val eventHandler = EventHandler(model, eventStore, objectStore)

        Api.start(eventHandler, args)
    }.mapLeft {
        logger.error { it }
    }

    private fun modelFilePath(): Either<MerodeError, Path> {
        val envPathString = System.getenv("MODEL_FILE_PATH")
        val pathSpecified = envPathString != null

        return if(pathSpecified) {
            val path = Path(envPathString)
            logger.info { "Starting API based on Model file at ${path.toAbsolutePath()}"}

            if(!path.toFile().exists()) {
                MerodeError("Model file not found at ${path.toAbsolutePath()}").left()
            } else {
                path.right()
            }
        } else {
            logger.info { "No Model file specified. Using default model file" }
            Path("src/test/resources/model2.mxp").right()
        }
    }
}