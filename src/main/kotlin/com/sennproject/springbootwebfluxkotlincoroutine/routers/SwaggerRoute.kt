package com.sennproject.springbootwebfluxkotlincoroutine.routers

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import java.net.URI

@Configuration
class SwaggerRoute {

    @Bean
    fun docsRouter() = router {
        GET("/") {
            ServerResponse.permanentRedirect(URI("/swagger-ui.html")).build()
        }
    }
}