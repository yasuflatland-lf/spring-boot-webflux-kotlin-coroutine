package com.sennproject.springbootwebfluxkotlincoroutine.handlers

import com.sennproject.springbootwebfluxkotlincoroutine.models.Todo
import com.sennproject.springbootwebfluxkotlincoroutine.repositories.TodoRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class TodoHandler(val repository: TodoRepository) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(TodoHandler::class.java)
    }

    suspend fun findAll(request: ServerRequest): ServerResponse {
        val todos = repository.findAll()
        return ok().contentType(APPLICATION_JSON).bodyAndAwait(todos);
    }

    suspend fun findAllByStatus(request: ServerRequest): ServerResponse {
        val page = request.pathVariable("page").toInt()
        val size = request.pathVariable("size").toInt()
        val sort = Sort.by(listOf(Sort.Order.desc("id")))
        val paging = PageRequest.of(page, size, sort)
        val todos = repository.findAllByStatusEquals(false, paging)
        return ok().contentType(APPLICATION_JSON).bodyAndAwait(todos);
    }

    suspend fun findById(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toLong()
        val todo = id.let { repository.findById(it) }
        return todo?.let { ok().contentType(APPLICATION_JSON).bodyValueAndAwait(it) }
            ?: ServerResponse.notFound().buildAndAwait()
    }

    suspend fun add(request: ServerRequest): ServerResponse {
        val todo = request.awaitBody<Todo>()
        repository.save(todo)
        return ok().buildAndAwait()
    }

    suspend fun delete(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toLong()
        repository.deleteById(id)
        return ok().buildAndAwait()
    }

}