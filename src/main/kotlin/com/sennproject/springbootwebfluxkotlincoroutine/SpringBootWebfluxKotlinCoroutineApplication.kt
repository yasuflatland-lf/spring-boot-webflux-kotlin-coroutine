package com.sennproject.springbootwebfluxkotlincoroutine

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springdoc.core.GroupedOpenApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


/**
 * @author Yasuyuki Takeo
 */
@SpringBootApplication
@OpenAPIDefinition(
    info = io.swagger.v3.oas.annotations.info.Info(
        title = "Todo Service",
        version = "1.0.0",
        description = "Springboot Webflux and Coroutine example implementations"

    )
)
class SpringBootWebfluxKotlinCoroutineApplication

fun main(args: Array<String>) {
    runApplication<SpringBootWebfluxKotlinCoroutineApplication>(*args)
}

@Bean
fun todosOpenApi(@Value("\${springdoc.version}") appVersion: String): GroupedOpenApi? {
    val paths = arrayOf("/todo/**")
    return GroupedOpenApi.builder().group("todo")
        .addOpenApiCustomiser {
            it.info(io.swagger.v3.oas.models.info.Info().title("TodoList API").version(appVersion))
        }
        .pathsToMatch(*paths)
        .build()
}