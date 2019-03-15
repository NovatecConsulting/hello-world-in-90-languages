package jvm.kotlin.micronaut

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
            .packages("jvm.kotlin.micronaut")
            .mainClass(Application.javaClass)
            .start()
    }

}
