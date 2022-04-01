package com.sennproject.springbootwebfluxkotlincoroutine.routers

import com.sennproject.springbootwebfluxkotlincoroutine.handlers.TodoHandler
import com.sennproject.springbootwebfluxkotlincoroutine.repositories.TodoRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class TodoRoutes {
    @Bean
    fun router(repository: TodoRepository) = coRouter {
        val handler = TodoHandler(repository)
        GET("/todo", handler::findAll)
    }
}