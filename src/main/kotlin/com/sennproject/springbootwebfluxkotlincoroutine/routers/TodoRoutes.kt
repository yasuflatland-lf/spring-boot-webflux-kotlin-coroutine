package com.sennproject.springbootwebfluxkotlincoroutine.routers

import com.sennproject.springbootwebfluxkotlincoroutine.handlers.TodoHandler
import com.sennproject.springbootwebfluxkotlincoroutine.repositories.TodoRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class TodoRoutes {

    // https://springdoc.org/#spring-webfluxwebmvc-fn-with-functional-endpoints
    // https://tech.uzabase.com/entry/2021/01/13/150022
    // https://github.com/spring-projects/spring-framework/issues/25938
    // https://springdoc.org/#spring-webfluxwebmvc-fn-with-functional-endpoints
    // https://github.dev/springdoc/springdoc-openapi/blob/master/springdoc-openapi-webflux-core/src/test/java/test/org/springdoc/api/app90/book/BookRouter.java
    @RouterOperations(
        RouterOperation(
            path = "/todo",
            method = [RequestMethod.GET],
            beanClass = TodoHandler::class,
            beanMethod = "findAll"
        ),
        RouterOperation(
            path = "/todo/{id}",
            method = [RequestMethod.GET],
            beanClass = TodoHandler::class,
            beanMethod = "findById",
            operation = Operation(
                operationId = "findById",
                summary = "Find a todo with the id",
                parameters = [
                    Parameter(`in` = ParameterIn.PATH, name = "id", description = "Todo ID"),
                ]
            )
        ),
        RouterOperation(
            path = "/todo/{id}",
            method = [RequestMethod.POST],
            beanClass = TodoHandler::class,
            beanMethod = "add",
            operation = Operation(
                operationId = "add",
                summary = "Add a todo",
            )
        ),
    )
    @Bean
    fun router(handler: TodoHandler) = coRouter {

        accept(APPLICATION_JSON).nest {
            GET("/todo/{id}", handler::findById)
            GET("/todo", handler::findAll)
        }
        POST("/todo", handler::add)
    }

}

