package com.sennproject.springbootwebfluxkotlincoroutine.handlers

import com.sennproject.springbootwebfluxkotlincoroutine.models.Todo
import com.sennproject.springbootwebfluxkotlincoroutine.repositories.TodoRepository
import kotlinx.coroutines.flow.flowOf
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status

/**
 * @author Yasuyuki Takeo
 */
@Component
class TodoHandler(val repository: TodoRepository) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(TodoHandler::class.java)
    }

    suspend fun findAllByStatus(request: ServerRequest): ServerResponse {
        return runCatching {
            val status = request.pathVariable("status").toBoolean()
            val page = request.pathVariable("page").toInt()
            val size = request.pathVariable("size").toInt()
            val sort = Sort.by(listOf(Sort.Order.desc("id")))
            val paging = PageRequest.of(page, size, sort)
            if (log.isDebugEnabled) {
                log.debug("paging : $paging")
            }
            repository.findAllByStatusEquals(status, paging)
        }.fold(
            onSuccess = {
                ok().contentType(APPLICATION_JSON).bodyAndAwait(it);
            },
            onFailure = {
                log.error(it.message)
                status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(APPLICATION_JSON)
                    .bodyAndAwait(flowOf(listOf<Todo>()))
            }
        )
    }

    suspend fun findById(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toLong()
        val todo = id.let { repository.findById(id) }
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