package com.oli.user

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.userModule() {
    configureUserRouting()
}

fun Application.configureUserRouting() {
   routing {
       userRouting()
   }
}
