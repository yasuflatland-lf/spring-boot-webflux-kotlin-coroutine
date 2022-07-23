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
            var status = request.queryParam("status").map { it.toBoolean() }.orElse(false)
            val page = request.queryParam("page").map { it.toInt() }.orElse(0)
            val size = request.queryParam("size").map { it.toInt() }.orElse(10)
            val sort = Sort.by(listOf(Sort.Order.desc("id")))
            val paging = PageRequest.of(page, size, sort)

            if (log.isDebugEnabled) {
                log.debug("status $status, page $page, size $size")
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

    suspend fun edit(request: ServerRequest): ServerResponse {
        return runCatching {
            val todo = request.awaitBody<Todo>()
            repository.save(todo)
        }.fold(
            onSuccess = {
                ok().contentType(APPLICATION_JSON).bodyAndAwait(flowOf(it));
            },
            onFailure = {
                log.error(it.message)
                status(HttpStatus.NOT_FOUND)
                    .contentType(APPLICATION_JSON)
                    .bodyAndAwait(flowOf(Todo(id = 0L, task = "", status = false)))
            }
        )
    }

    suspend fun delete(request: ServerRequest): ServerResponse {
        return runCatching {
            val id = request.pathVariable("id").toLong()
            repository.deleteById(id)
        }.fold(
            onSuccess = {
                ok().contentType(APPLICATION_JSON).bodyAndAwait(flowOf(it));
            },
            onFailure = {
                log.error(it.message)
                status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(APPLICATION_JSON)
                    .bodyAndAwait(flowOf(Todo(id = 0L, task = "", status = false)))
            }
        )
    }

}