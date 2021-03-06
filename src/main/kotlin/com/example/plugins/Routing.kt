package com.example.plugins

import com.example.dao.dao
import com.example.models.Article
import com.example.models.articles
import com.example.routes.customerRouting
import com.example.routes.getOrdersRoute
import com.example.routes.listOrdersRoute
import com.example.routes.totalizeOrderRoute
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.configureRouting() {
    routing {
        customerRouting()
        listOrdersRoute()
        getOrdersRoute()
        totalizeOrderRoute()

        static("/static") {
            resources("files")
        }
        route("articles") {
            get {
                call.respond(FreeMarkerContent("index.ftl", mapOf("articles" to articles)))
            }
            get("new") {
                call.respond(FreeMarkerContent("new.ftl", model = null))
            }
            post {
                val formParameters = call.receiveParameters()
                val title = formParameters.getOrFail("title")
                val body = formParameters.getOrFail("body")
                val newEntry = Article.newEntry(title, body)
                articles.add(newEntry)
                call.respondRedirect("/articles/${newEntry.id}")
            }
            get("{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                call.respond(FreeMarkerContent("show.ftl", mapOf("article" to articles.find { it.id == id })))
            }
            get("{id}/edit") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                call.respond(FreeMarkerContent("edit.ftl", mapOf("article" to articles.find { it.id == id })))
            }
            post("{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                val formParameters = call.receiveParameters()
                when (formParameters.getOrFail("_action")) {
                    "update" -> {
                        val title = formParameters.getOrFail("title")
                        val body = formParameters.getOrFail("body")
                        articles[id].title = title
                        articles[id].body = body
                        call.respondRedirect("/articles/$id")
                    }
                    "delete" -> {
                        articles.removeIf { it.id == id }
                        call.respondRedirect("/articles")
                    }
                }
            }

        }


        get {
            call.respond(FreeMarkerContent("index.ftl", mapOf("articles" to dao.allArticles())))
        }

        post {
            val formParameters = call.receiveParameters()
            val title = formParameters.getOrFail("title")
            val body = formParameters.getOrFail("body")
            val article = dao.addNewArticle(title, body)
            call.respondRedirect("/articles/${article?.id}")
        }

        get("{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(FreeMarkerContent("show.ftl", mapOf("article" to dao.article(id))))
        }
        get("{id}/edit") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(FreeMarkerContent("edit.ftl", mapOf("article" to dao.article(id))))
        }

        post("{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val formParameters = call.receiveParameters()
            when (formParameters.getOrFail("_action")) {
                "update" -> {
                    val title = formParameters.getOrFail("title")
                    val body = formParameters.getOrFail("body")
                    dao.editArticle(id, title, body)
                    call.respondRedirect("/articles/$id")
                }
                "delete" -> {
                    dao.deleteArticle(id)
                    call.respondRedirect("/articles")
                }
            }
        }
    }
}
