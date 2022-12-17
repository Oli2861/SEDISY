package com.oli.user

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

object UserRouteConstants{
    const val USER_ROUTE_PATH = "/user"
}

fun Route.userRouting() {

    route(UserRouteConstants.USER_ROUTE_PATH) {

        authenticate("auth-basic-hashed") {
            get {
                call.respond(User("", "Max", "mail"))
            }
        }

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
