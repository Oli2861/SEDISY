package com.oli.user

import com.oli.persistence.UserDAOImpl
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking

fun Application.userModule() {
    configureUserRouting()
}

fun Application.configureUserRouting() {
    val userDAO = UserDAOImpl().apply {
        runBlocking {
            // Add admin user if empty
            if (readAll().isEmpty()) {
                create(User(0, "admin", "1234", "mail@mail.com"))
            }
        }
    }

    val userService = UserService(userDAO, this.log)

    routing {
        userRouting(userService)
    }
}
