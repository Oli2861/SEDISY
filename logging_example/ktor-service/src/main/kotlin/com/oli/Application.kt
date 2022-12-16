package com.oli

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.oli.plugins.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.request.*
import org.slf4j.event.Level
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

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
    Scheduler {
        log.trace("Test: Trace")
        log.debug("Test: Debug")
        log.info("Test: Info")
        log.warn("Test: Warning")
    }.scheduleExecution(Every(3, TimeUnit.SECONDS))
}

class Scheduler(private val task: Runnable) {
    private val executor = Executors.newScheduledThreadPool(1)!!

    fun scheduleExecution(every: Every) {
        val taskWrapper = Runnable {
            task.run()
        }
        executor.scheduleWithFixedDelay(taskWrapper, every.n, every.n, every.unit)
    }

    fun stop() {
        executor.shutdown()
        try {
            executor.awaitTermination(1, TimeUnit.HOURS)
        } catch (_: InterruptedException) {
        }

    }
}

data class Every(val n: Long, val unit: TimeUnit)