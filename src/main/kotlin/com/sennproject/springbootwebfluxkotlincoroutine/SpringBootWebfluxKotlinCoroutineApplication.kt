package com.sennproject.springbootwebfluxkotlincoroutine

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

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
