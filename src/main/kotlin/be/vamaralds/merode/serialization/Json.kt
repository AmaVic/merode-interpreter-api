package be.vamaralds.merode.serialization

import arrow.core.Either
import arrow.core.EitherNel
import arrow.core.left
import arrow.core.raise.catch
import arrow.core.raise.either
import arrow.core.right
import be.vamaralds.merode.common.MerodeError
import org.json.JSONException
import org.json.JSONObject

class SerializationError(message: String) : MerodeError(message)

interface JsonSerializable {
    fun toJsonString(): String =
        JSONObject(this).toString()
}

fun interface JsonDeserializable<T> {
    fun fromJsonString(json: String): Either<SerializationError, T>
}

fun<T> safe(jsonString: String, block: JSONObject.() -> T): Either<SerializationError, T> =
    try {
        val jsonObject = JSONObject(jsonString)
        jsonObject.block().right()
    } catch(e: JSONException) {
        SerializationError(e.toString()).left()
    }
