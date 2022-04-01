package com.sennproject.springbootwebfluxkotlincoroutine.routers

import com.sennproject.springbootwebfluxkotlincoroutine.handlers.TodoHandler
import com.sennproject.springbootwebfluxkotlincoroutine.repositories.TodoRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class TodoRoutes {
    @Bean
    fun router(repository: TodoRepository) = coRouter {
        val handler = TodoHandler(repository)
        accept(APPLICATION_JSON).nest {
            GET("/person/{id}", handler::getTodo)
            GET("/todo", handler::findAll)
        }
        POST("/todo", handler::addTodo)
    }
}