package be.vamaralds.merode.api.model

import arrow.core.*
import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
import be.vamaralds.merode.common.MerodeError
import be.vamaralds.merode.instance.Event
import be.vamaralds.merode.instance.EventHandler
import be.vamaralds.merode.instance.InstanceError
import be.vamaralds.merode.model.Model
import be.vamaralds.merode.serialization.safe
import org.json.JSONException
import org.json.JSONObject
import java.lang.ClassCastException

data class NewEvent(val type: String, val objectId: Long = -1, val properties: Map<String, Any> = emptyMap(), val masters: Map<String, Long> = emptyMap()) {
    fun toJsonString(): String =
        JSONObject(this).toString()

    companion object {
        fun fromJsonString(json: String): EitherNel<MerodeError, NewEvent> = either {
            val jsonObject = JSONObject(json)
            zipOrAccumulate(
                {
                    try {
                        jsonObject.getString("type").right()
                    } catch (e: JSONException) {
                        MerodeError("type property is missing in the provided JSON").left()
                    }.bind()
                },
                {
                    try {
                        jsonObject.getLong("objectId").right()
                    } catch (e: JSONException) {
                        (-1L).right()
                    }.bind()
                },
                {
                    try {
                        jsonObject.getJSONObject("properties").toMap().right()
                    } catch (e: JSONException) {
                        emptyMap<String, Any>().right()
                    }.bind()
                },
                {
                    try {
                        jsonObject.getJSONObject("masters").toMap().right()
                    } catch(e: JSONException) {
                        emptyMap<String, Long>().right()
                    }.bind()
                }
            ) { type, targetId, properties, masters ->
                NewEvent(type, targetId, properties, masters as Map<String, Long>)
            }
        }
    }

    context(Model)
    fun toEvent(eventHandler: EventHandler): EitherNel<InstanceError, Event> = either {
        val eventType = eventHandler.model.eventTypes.find { it.name == type } ?: raise(InstanceError("EventType $type not found").nel())
        val eventId = objectId
        eventType(eventId, objectId, properties, masters).bind()
    }
}