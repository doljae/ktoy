package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.server.application.*

fun main() {
    embeddedServer(Netty, port = 1234, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureSerialization()
}