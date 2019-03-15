package jvm.kotlin.micronaut

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue

@Controller("/say-hello{?name}")
class SayHelloController {

    @Post
    fun post(@QueryValue("name") name: String?) = Response("Hello $name")

}