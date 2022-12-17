package com.oli.user

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRouting() {

    route("/user") {

        get("{id?}") {
            val userId =
                call.parameters["id"] ?: return@get call.respondText("Missing userId", status = HttpStatusCode.BadRequest)
            call.application.log.info("Requesting user $userId")

            call.respondText("Tbd")
        }

        post {
            val user = call.receive<User>()
            call.application.log.info("Creating user $user")

            call.respondText("Tbd")
        }

    }

}
