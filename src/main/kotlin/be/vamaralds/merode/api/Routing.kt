package be.vamaralds.merode.api

import arrow.core.Nel
import arrow.core.nel
import arrow.core.raise.either
import be.vamaralds.merode.MerodeApplication
import be.vamaralds.merode.api.model.NewEvent
import be.vamaralds.merode.common.MerodeError
import be.vamaralds.merode.instance.BusinessObject
import be.vamaralds.merode.instance.InstanceError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/event") {
            either {
                val events = Api.eventHandler!!.eventStore.getAll()
                    .mapLeft { MerodeError(it.toString()).nel() }
                    .bind()

                val jsonResponse = "[" + events.joinToString(", ") { it.toJsonString() } + "]"
                call.respond(HttpStatusCode.OK, jsonResponse)
            }.mapLeft {
                val errorsList = it.toList()
                val errorsJson = "[" + errorsList.joinToString(", ") { it.toString() } + "]"
                call.respond(HttpStatusCode.BadRequest, errorsJson)
            }
        }
        post("/event") {
            with(Api.eventHandler!!.model) {
                val bodyText = call.receiveText()
                either {
                    val affectedObjects = either {
                        val newEvent = NewEvent.fromJsonString(bodyText)
                            .mapLeft { MerodeError(it.toString()).nel() }
                            .bind()

                        val event = newEvent.toEvent(Api.eventHandler!!).bind()
                        Api.eventHandler!!.handleEvent(event).bind()
                    }.bind()

                    val jsonResponse = "[" + affectedObjects.joinToString(", ") { it.toJsonString() } + "]"
                    call.respond(HttpStatusCode.OK, jsonResponse)
                }.mapLeft { errors ->
                    val errorsList = errors.toList()
                    val errorsJson = "[" + errorsList.joinToString(", ") { it.toString() } + "]"
                    call.respond(HttpStatusCode.BadRequest, errorsJson)
                }
            }
        }

        get("/{objectTypeName}") {
            either {
                var typeName = call.parameters["objectTypeName"] ?: raise(InstanceError("No object type name provided").nel())
                typeName = typeName.replaceFirstChar { it.uppercaseChar() }

                val objectType = Api.eventHandler!!.model.objectType(typeName)
                    .mapLeft { MerodeError(it.toString()).nel() }
                    .bind()

                val objects = Api.eventHandler!!.objectStore.getAll(objectType.name)
                    .mapLeft { MerodeError(it.toString()).nel() }
                    .bind()

                val jsonResponse = "[" + objects.joinToString(", ") { it.toJsonString() } + "]"
                call.respond(HttpStatusCode.OK, jsonResponse)
            }.mapLeft {
                val errorsList = it.toList()
                val errorsJson = "[" + errorsList.joinToString(", ") { it.toString() } + "]"
                call.respond(HttpStatusCode.BadRequest, errorsJson)
            }
        }

        get("/{objectTypeName}/{objectId}") {
            either<Nel<MerodeError>, Unit> {
                var typeName = call.parameters["objectTypeName"] ?: raise(InstanceError("No object type name provided").nel())
                typeName = typeName.replaceFirstChar { it.uppercaseChar() }

                val objectId = call.parameters["objectId"]?.toLong() ?: raise(InstanceError("No object id provided").nel())
                val objectType = Api.eventHandler!!.model.objectType(typeName)
                    .mapLeft { MerodeError(it.toString()).nel() }
                    .bind()

                val objectToGet = Api.eventHandler!!.objectStore.get(objectId)
                    .mapLeft { MerodeError(it.toString()).nel() }
                    .bind()

                if (objectToGet.type != objectType) {
                    raise(InstanceError("Object with id $objectId is not of type $typeName").nel())
                }

                call.respond(HttpStatusCode.OK, objectToGet.toJsonString())
            }.mapLeft {
                val errorsList = it.toList()
                val errorsJson = "[" + errorsList.joinToString(", ") { it.toString() } + "]"
                call.respond(HttpStatusCode.BadRequest, errorsJson)
            }
        }
    }
}