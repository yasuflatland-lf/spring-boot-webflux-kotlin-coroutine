package com.sennproject.springbootwebfluxkotlincoroutine.routers

import com.sennproject.springbootwebfluxkotlincoroutine.handlers.TodoHandler
import com.sennproject.springbootwebfluxkotlincoroutine.models.Todo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
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
            path = "/todos",
            method = [RequestMethod.GET],
            operation = Operation(
                operationId = "getTodoById",
                summary = "Find a todos by id",
                requestBody = RequestBody(
                    content = [Content(schema = Schema(implementation = Todo::class))]
                ),
                responses = [
                    ApiResponse(
                        responseCode = "200",
                        content = [Content(schema = Schema(implementation = Todo::class))]
                    ),
                    ApiResponse(
                        responseCode = "404",
                        description = "no Todo with such id found"
                    )
                ]
            )
        ),
        RouterOperation(
            path = "/todo",
            method = [RequestMethod.GET],
            operation = Operation(
                operationId = "findAllByStatus",
                summary = "Find all todos by status",
                parameters = [
                    Parameter(`in` = ParameterIn.PATH, name = "status", description = "status"),
                    Parameter(`in` = ParameterIn.PATH, name = "size", description = "size"),
                    Parameter(`in` = ParameterIn.PATH, name = "size", description = "size"),
                ],
                responses = [
                    ApiResponse(
                        responseCode = "200",
                        content = [Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = ArraySchema(
                                schema = Schema(implementation = Todo::class)
                            )
                        )]
                    ),
                    ApiResponse(
                        responseCode = "404",
                        description = "no Todo with such id found"
                    )
                ]
            )
        ),
        RouterOperation(
            path = "/todo",
            method = [RequestMethod.POST],
            operation = Operation(
                operationId = "edit",
                summary = "Add a todo",
                requestBody = RequestBody(
                    content = [Content(schema = Schema(implementation = Todo::class))]
                ),
                responses = [
                    ApiResponse(
                        responseCode = "200",
                        description = "succeeded",
                        content = [Content(schema = Schema(implementation = Todo::class))]
                    ),
                    ApiResponse(
                        responseCode = "404",
                        description = "Can not add Todo"
                    )
                ]
            )
        ),
        RouterOperation(
            path = "/todo",
            method = [RequestMethod.PATCH],
            operation = Operation(
                operationId = "edit",
                summary = "Edit a todo",
                requestBody = RequestBody(
                    content = [Content(schema = Schema(implementation = Todo::class))]
                ),
                responses = [
                    ApiResponse(
                        responseCode = "200",
                        description = "succeeded",
                        content = [Content(schema = Schema(implementation = Todo::class))]
                    ),
                    ApiResponse(
                        responseCode = "404",
                        description = "Can not edit Todo with the id"
                    )
                ]
            )
        ),
        RouterOperation(
            path = "/todo",
            method = [RequestMethod.DELETE],
            operation = Operation(
                operationId = "delete",
                summary = "Delete a todo by id",
                parameters = [
                    Parameter(`in` = ParameterIn.PATH, name = "id", description = "Todo ID"),
                ],
                responses = [
                    ApiResponse(
                        responseCode = "200",
                        description = "succeeded",
                        content = [Content(schema = Schema(implementation = Todo::class))]
                    ),
                    ApiResponse(
                        responseCode = "404",
                        description = "Can not delete Todo with the id"
                    )
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

