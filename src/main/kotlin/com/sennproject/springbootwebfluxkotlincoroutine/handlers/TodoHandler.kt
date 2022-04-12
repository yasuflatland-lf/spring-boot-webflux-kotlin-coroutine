package com.sennproject.springbootwebfluxkotlincoroutine.handlers

import com.sennproject.springbootwebfluxkotlincoroutine.models.Todo
import com.sennproject.springbootwebfluxkotlincoroutine.repositories.TodoRepository
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class TodoHandler(val repository: TodoRepository) {

    suspend fun findAll(request: ServerRequest): ServerResponse {
        val todos = repository.findAll()
        return ok().contentType(APPLICATION_JSON).bodyAndAwait(todos);
    }

    suspend fun findById(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toLong()
        val todo = id.let { repository.findById(it) }
        return todo?.let { ok().contentType(APPLICATION_JSON).bodyValueAndAwait(it) }
            ?: ServerResponse.notFound().buildAndAwait()
    }

    suspend fun add(request: ServerRequest): ServerResponse {
        val person = request.awaitBody<Todo>()
        repository.save(person)
        return ok().buildAndAwait()
    }

//    val delete = HandlerFunction { request: ServerRequest? -> suspend {
//        val id = request?.pathVariable("id")?.toLong()
//        return repository.deleteById(todoId)?.let { ok().contentType(APPLICATION_JSON).bodyValueAndAwait(it) }
//
//    }
//    suspend fun deleteTodo(request: ServerRequest): ServerResponse {
//        val todoId = request.pathVariable("id").toLong()
//        return repository.deleteById(todoId)?.let { ok().contentType(APPLICATION_JSON).bodyValueAndAwait(it) }
//    }
}