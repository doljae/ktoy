package com.example.plugins

import com.example.routes.customerRouting
import com.example.routes.getOrdersRoute
import com.example.routes.listOrdersRoute
import com.example.routes.totalizeOrderRoute
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        customerRouting()
        listOrdersRoute()
        getOrdersRoute()
        totalizeOrderRoute()

        static("/static") {
            resources("files")
        }
    }
}
