package com.example

import com.example.dao.DatabaseFactory
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import com.example.plugins.configureTemplating
import io.ktor.server.application.*

//fun main() {
//    embeddedServer(Netty, port = 1234, host = "0.0.0.0") {
//        configureRouting()
//    }.start(wait = true)
//}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)


fun Application.module() {
//    install(ContentNegotiation)
//    install(Routing)
    configureRouting()
    configureSerialization()
    configureTemplating()
    DatabaseFactory.init()
}