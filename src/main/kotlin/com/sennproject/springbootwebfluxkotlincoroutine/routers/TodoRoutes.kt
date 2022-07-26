package com.sennproject.springbootwebfluxkotlincoroutine.routers

import com.sennproject.springbootwebfluxkotlincoroutine.handlers.TodoHandler
import com.sennproject.springbootwebfluxkotlincoroutine.models.Todo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import kotlinx.coroutines.FlowPreview
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
//    @RouterOperations(
//        RouterOperation(
//            path = "/todo",
//            method = [RequestMethod.GET],
//            beanClass = TodoHandler::class,
//            beanMethod = "findAllByStatus",
//            operation = Operation(
//                operationId = "findAllByStatus",
//                summary = "Find all todos by status",
//                parameters = [
//                    Parameter(`in` = ParameterIn.PATH, name = "status", description = "status"),
//                    Parameter(`in` = ParameterIn.PATH, name = "size", description = "size"),
//                    Parameter(`in` = ParameterIn.PATH, name = "size", description = "size"),
//                ],
//                responses = [
//                    ApiResponse(
//                        responseCode = "200",
//                        description = "succeeded"
//                    )
//                ]
//            )
//        ),
//        RouterOperation(
//            path = "/todo/{id}",
//            method = [RequestMethod.GET],
//            beanClass = TodoHandler::class,
//            beanMethod = "findById",
//            operation = Operation(
//                operationId = "findById",
//                summary = "Find a todo by id",
//                parameters = [
//                    Parameter(`in` = ParameterIn.PATH, name = "id", description = "Todo ID"),
//                ],
//                responses = [
//                    ApiResponse(
//                        responseCode = "200",
//                        description = "succeeded",
//                        content = [Content(schema = Schema(implementation = Todo::class))]
//                    )
//                ]
//
//            )
//        ),
//        RouterOperation(
//            path = "/todo",
//            method = [RequestMethod.POST],
//            beanClass = TodoHandler::class,
//            beanMethod = "edit",
//            operation = Operation(
//                operationId = "edit",
//                summary = "Add a todo",
//                responses = [
//                    ApiResponse(
//                        responseCode = "200",
//                        description = "succeeded",
//                        content = [Content(schema = Schema(implementation = Todo::class))]
//                    )
//                ]
//
//            )
//        ),
//        RouterOperation(
//            path = "/todo",
//            method = [RequestMethod.PATCH],
//            beanClass = TodoHandler::class,
//            beanMethod = "edit",
//            operation = Operation(
//                operationId = "edit",
//                summary = "Edit a todo",
//                responses = [
//                    ApiResponse(
//                        responseCode = "200",
//                        description = "succeeded",
//                        content = [Content(schema = Schema(implementation = Todo::class))]
//                    )
//                ]
//            )
//        ),
//        RouterOperation(
//            path = "/todo",
//            method = [RequestMethod.DELETE],
//            beanClass = TodoHandler::class,
//            beanMethod = "delete",
//            operation = Operation(
//                operationId = "delete",
//                summary = "Delete a todo by id",
//                parameters = [
//                    Parameter(`in` = ParameterIn.PATH, name = "id", description = "Todo ID"),
//                ],
//                responses = [
//                    ApiResponse(
//                        responseCode = "200",
//                        description = "succeeded",
//                        content = [Content(schema = Schema(implementation = Todo::class))]
//                    )
//                ]
//            )
//        ),
//    )
    @RouterOperations(
        RouterOperation(
            path = "/todo",
            method = [RequestMethod.GET],
            operation = Operation(
                operationId = "check-if-password-is-valid",
                requestBody = RequestBody(
                    content = [Content(schema = Schema(implementation = Todo::class))]
                ),
                responses = [
                    ApiResponse(
                        responseCode = "200",
                        content = [Content(schema = Schema(implementation = Todo::class))]
                    )
                ]
            )
        )
    )
    fun router(handler: TodoHandler) = coRouter {

        accept(APPLICATION_JSON).nest {
            GET("/todo/{id}", handler::findById)
            GET("/todo", handler::findAllByStatus)
        }
        POST("/todo", handler::edit)
        PATCH("/todo", handler::edit)
        DELETE("/todo/{id}", handler::delete)
    }
}

