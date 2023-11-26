package be.vamaralds.merode.api.model

import arrow.core.Either
import arrow.core.EitherNel
import arrow.core.nel
import arrow.core.raise.either
import be.vamaralds.merode.common.MerodeError
import be.vamaralds.merode.instance.Event
import be.vamaralds.merode.instance.EventHandler
import be.vamaralds.merode.instance.InstanceError
import be.vamaralds.merode.model.EventType
import org.json.JSONException
import org.json.JSONObject

data class NewEvent(val type: String, val targetId: Long = -1, val properties: Map<String, Any> = emptyMap()) {
    fun toJsonString(): String =
        JSONObject(this).toString()

    companion object {
        fun fromJsonString(json: String): NewEvent {
            val jsonObj = JSONObject(json)
            val typeName = jsonObj.getString("type")
            val targetId = try { jsonObj.getLong("targetId") } catch (e: JSONException) { -1 }
            val props = try { jsonObj.getJSONObject("properties").toMap() } catch (e: JSONException) { emptyMap() }

            return NewEvent(typeName, targetId, props)
        }
    }

    fun toEvent(eventHandler: EventHandler): EitherNel<InstanceError, Event> = either {
        val eventType = eventHandler.model.eventTypes.find { it.name == type } ?: raise(InstanceError("EventType $type not found").nel())
        val eventId = targetId
        eventType(eventId, targetId, properties).bind()
    }
}