package be.vamaralds.merode.mxp

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import be.vamaralds.merode.model.Model
import be.vamaralds.merode.model.ModelParsingError
import java.nio.file.Path

object Parser {
    fun parseMxp(mxpFilePath: Path): Either<ModelParsingError, Model> = either {
        val mxpFile = mxpFilePath.toFile()
        ensure(mxpFile.exists()) { ModelParsingError("File ${mxpFile.absolutePath} does not exist") }

        TODO()
    }
}