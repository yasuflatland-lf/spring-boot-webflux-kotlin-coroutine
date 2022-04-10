package com.sennproject.springbootwebfluxkotlincoroutine

import io.swagger.v3.core.converter.ModelConverters
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springdoc.webflux.core.converters.WebFluxSupportConverter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.config.EnableWebFlux

@EnableWebFlux
@SpringBootApplication
class SpringBootWebfluxKotlinCoroutineApplication

fun main(args: Array<String>) {
    runApplication<SpringBootWebfluxKotlinCoroutineApplication>(*args)
}

@Bean
fun customOpenAPI(): OpenAPI? {
    ModelConverters.getInstance().addConverter(WebFluxSupportConverter())
    return OpenAPI()
        .info(
            Info().title("Todo API").version("v1")
                .license(License().name("Apache 2.0").url("http://springdoc.org"))
                .description("Todo API sample application")
                .version("v1")
        )
}
