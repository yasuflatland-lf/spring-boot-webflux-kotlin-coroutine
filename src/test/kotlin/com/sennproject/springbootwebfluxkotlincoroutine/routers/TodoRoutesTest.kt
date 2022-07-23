package com.sennproject.springbootwebfluxkotlincoroutine.routers

import com.sennproject.springbootwebfluxkotlincoroutine.AbstractContainerBaseTest
import com.sennproject.springbootwebfluxkotlincoroutine.models.Todo
import com.sennproject.springbootwebfluxkotlincoroutine.repositories.TodoRepository
import com.sennproject.springbootwebfluxkotlincoroutine.utils.TodoTestUtils
import io.kotest.core.annotation.AutoScan
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.reactive.function.BodyInserters
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.core.publisher.Mono

@AutoScan
@Testcontainers
@OptIn(ExperimentalCoroutinesApi::class, ExperimentalStdlibApi::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(initializers = [AbstractContainerBaseTest.Initializer::class])
class TodoRoutesTest : FunSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var todoRepository: TodoRepository

    @LocalServerPort
    private var port: Int = 0

    init {

        beforeEach {
            todoRepository.deleteAll()
        }

        test("Get all todos Smoke Test") {
            var todo = arrayListOf<Todo>()

            WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:$port")
                .build()
                .get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path("/todo")
                        .queryParam("state", true)
                        .queryParam("page", 2)
                        .queryParam("size", 3)
                        .build()
                }
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful
                .expectBody<List<Todo>>()
                .isEqualTo(todo)
        }

        test("Get all todos no params Test") {
            var todo = arrayListOf<Todo>()

            WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:$port")
                .build()
                .get()
                .uri("/todo")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful
                .expectBody<List<Todo>>()
                .isEqualTo(todo)
        }

        test("Add Smoke test") {
            var todo = Todo(null)

            WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:$port")
                .build()
                .post()
                .uri("/todo")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(todo), Todo::class.java))
                .exchange()
                .expectStatus()
                .is2xxSuccessful
        }

        test("Edit Smoke test") {
            // Create a todo
            var todo = Todo(null)
            var result = todoRepository.save(todo)
            val beforeAmount = todoRepository.count()
            beforeAmount shouldBe 1

            // Change task string
            result.task = "changed"

            WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:$port")
                .build()
                .patch()
                .uri("/todo")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(result), Todo::class.java))
                .exchange()
                .expectStatus()
                .is2xxSuccessful

            var changedResult = result.id?.let { todoRepository.findById(it) }

            // Must be properly edited.
            if (changedResult != null) {
                changedResult.task shouldBe result.task
            }
        }

        test("Delete Smoke test") {
            // Create a todo
            var todo = Todo(null)
            val result = todoRepository.save(todo)
            val beforeAmount = todoRepository.count()
            beforeAmount shouldBe 1

            WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:$port")
                .build()
                .delete()
                .uri { uriBuilder ->
                    uriBuilder
                        .pathSegment("todo", result.id.toString())
                        .build()
                }
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful

            val afterAmount = todoRepository.count()
            afterAmount shouldBe 0
        }

        test("Edit Error test") {
            // Create a todo
            var result = todoRepository.save(Todo(null))
            val beforeAmount = todoRepository.count()
            beforeAmount shouldBe 1

            // Change task string
            result.task = "changed"
            result.id = 1000

            WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:$port")
                .build()
                .patch()
                .uri("/todo")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(result), Todo::class.java))
                .exchange()
                .expectStatus()
                .is4xxClientError

        }

        test("Get all todos data Test") {
            val todoAmount: Long = 10
            val trueAmount = 3
            var todos = TodoTestUtils.createRandomList(todoAmount, trueAmount)

            val check = todoRepository.saveAll(todos)
            check.count() shouldBe todoAmount

            WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:$port")
                .build()
                .get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path("/todo")
                        .queryParam("status", true)
                        .queryParam("page", 0)
                        .queryParam("size", 5)
                        .build()
                }
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful
                .expectBodyList(Todo::class.java)
                .hasSize(trueAmount)
                .returnResult()

        }
    }

}