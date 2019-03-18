package info.novatec.ktor

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    val server = embeddedServer(Netty, port = 8080) {
        routing {
            install(ContentNegotiation) {
                jackson { }
            }
            get("/say-hello") {
                val name = call.request.queryParameters["name"]
                call.respond(Response("Hello $name!"))
            }
        }
    }
    server.start(wait = true)
}

data class Response(val message: String)
