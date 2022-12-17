package com.oli

import com.oli.plugins.configureHTTP
import com.oli.plugins.configureRouting
import com.oli.plugins.configureSecurity
import com.oli.plugins.configureSerialization
import com.oli.user.userModule
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.request.*
import org.slf4j.event.Level

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    configureSecurity()
    configureHTTP()
    //configureMonitoring()
    configureSerialization()
    configureRouting()

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
    userModule()

}