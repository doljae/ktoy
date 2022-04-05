package com.example.plugins

import io.ktor.shared.serialization.kotlinx.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}
