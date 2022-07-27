package com.sennproject.springbootwebfluxkotlincoroutine.routers

import com.sennproject.springbootwebfluxkotlincoroutine.handlers.TodoHandler
import com.sennproject.springbootwebfluxkotlincoroutine.models.Todo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.function.server.coRouter

/**
 * @author Yasuyuki Takeo
 */
@Configuration
class TodoRoutes {

    // https://springdoc.org/#spring-webfluxwebmvc-fn-with-functional-endpoints
    // https://tech.uzabase.com/entry/2021/01/13/150022
    // https://github.com/spring-projects/spring-framework/issues/25938
    // https://github.dev/springdoc/springdoc-openapi/blob/master/springdoc-openapi-webflux-core/src/test/java/test/org/springdoc/api/app90/book/BookRouter.java
    @Bean
    @RouterOperations(
        RouterOperation(
            path = "/todos/{id}",
            method = [RequestMethod.GET],
            operation = Operation(
                operationId = "getTodoById",
                summary = "Find a todo by id",
                tags = ["Todos"],
                parameters = [
                    Parameter(`in` = ParameterIn.PATH, name = "id", description = "id"),
                ]
            )
        ),
        RouterOperation(
            path = "/todos",
            method = [RequestMethod.GET],
            operation = Operation(
                operationId = "findAllByStatus",
                summary = "Find all todos by status",
                tags = ["Todos"],
                parameters = [
                    Parameter(`in` = ParameterIn.QUERY, name = "status", description = "status"),
                    Parameter(`in` = ParameterIn.QUERY, name = "page", description = "page"),
                    Parameter(`in` = ParameterIn.QUERY, name = "size", description = "size"),
                ]
            )
        ),
        RouterOperation(
            path = "/todos",
            method = [RequestMethod.POST],
            operation = Operation(
                operationId = "edit",
                summary = "Add a todo",
                tags = ["Todos"],
                requestBody = RequestBody(
                    content = [Content(schema = Schema(implementation = Todo::class))]
                )
            )
        ),
        RouterOperation(
            path = "/todos",
            method = [RequestMethod.PATCH],
            operation = Operation(
                operationId = "edit",
                summary = "Edit a todo",
                tags = ["Todos"],
                requestBody = RequestBody(
                    content = [Content(schema = Schema(implementation = Todo::class))]
                )
            )
        ),
        RouterOperation(
            path = "/todos/{id}",
            method = [RequestMethod.DELETE],
            operation = Operation(
                operationId = "delete",
                summary = "Delete a todo by id",
                tags = ["Todos"],
                parameters = [
                    Parameter(`in` = ParameterIn.PATH, name = "id", description = "id"),
                ]
            )
        ),
    )
    fun router(handler: TodoHandler) = coRouter {

        accept(APPLICATION_JSON).nest {
            GET("/todos/{id}", handler::findById)
            GET("/todos", handler::findAllByStatus)
        }
        POST("/todos", handler::edit)
        PATCH("/todos", handler::edit)
        DELETE("/todos/{id}", handler::delete)
    }
}

