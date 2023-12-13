package be.vamaralds.merode.cli

import be.vamaralds.merode.MerodeApplication
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import kotlinx.coroutines.runBlocking

class StartCommand(private val args: Array<String> = emptyArray()): CliktCommand() {
    val modelFile by option("-m", "--model", help = "Path to the model (.mxp) file").file(mustExist = true, canBeDir = false).required()

    override fun run() {
        runBlocking {
            MerodeApplication.start(modelFile.toPath(), args)
        }
    }
}