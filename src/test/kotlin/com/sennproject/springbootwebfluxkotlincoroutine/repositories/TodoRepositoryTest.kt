package com.sennproject.springbootwebfluxkotlincoroutine.repositories

import com.sennproject.springbootwebfluxkotlincoroutine.AbstractContainerBaseTest
import com.sennproject.springbootwebfluxkotlincoroutine.models.Todo
import io.kotest.core.annotation.Isolate
import io.kotest.core.extensions.ApplyExtension
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.LocalDateTime

@ApplyExtension(SpringExtension::class)
@Testcontainers
@SpringBootTest(
    properties = ["spring.main.web-application-type=reactive"],
    args = ["-opt-in=kotlin.RequiresOptIn"])
@Isolate
@ActiveProfiles("test")
@ContextConfiguration(initializers = [AbstractContainerBaseTest.Initializer::class])
class TodoRepositoryTest : FunSpec() {

    @Autowired
    private lateinit var todoRepository: TodoRepository

    init {
        beforeEach {
            todoRepository.deleteAll()
        }

        test("Save") {
            var todo = Todo(null)
            todo.task = "test"
            val result = todoRepository.save(todo)
            result.id shouldNotBe 0
            result.task shouldBe "test"
            result.status shouldBe false
        }

        test("Delete") {
            // Save
            var todo = Todo(null)
            todo.task = "test"
            var result = todoRepository.save(todo)
            var amount = todoRepository.count()
            amount shouldBe 1

            // Delete
            todoRepository.delete(result)
            amount = todoRepository.count()
            amount shouldBe 0
        }

        test("findAllByStatusEquals success pattern") {
            forAll(
                row(true, true, 1),
                row(true, false, 0),
                row(false, false, 1),
                row(false, true, 2),
            ) { orgStatus, filterStatus, amount ->

                runTest {
                    var todo = Todo(null)
                    todo.task = "test"
                    todo.status = orgStatus
                    todoRepository.save(todo)

                    var result = todoRepository.findAllByStatusEquals(filterStatus, PageRequest.of(0, 20))
                    result.count() shouldBe amount
                }
            }
        }

        test("findById") {
            var todo = Todo(null)
            todo.task = "test"
            val saved = todoRepository.save(todo)

            val result = todoRepository.findById(saved.id!!)
            result shouldNotBe null
            result?.id shouldBe saved.id
            result?.task shouldBe "test"
        }

        // todos.created_at / updated_at are TIMESTAMPTZ while Todo maps them to LocalDateTime,
        // so the driver has to apply and strip an offset. A fixed timestamp keeps this from
        // passing on precision alone.
        test("Timestamps survive a TIMESTAMPTZ round trip") {
            val fixed = LocalDateTime.of(2026, 1, 2, 3, 4, 5)
            var todo = Todo(null, created_at = fixed, updated_at = fixed)
            todo.task = "test"
            val saved = todoRepository.save(todo)

            val result = todoRepository.findById(saved.id!!)
            result?.created_at shouldBe fixed
            result?.updated_at shouldBe fixed
        }

        test("findAllByStatusEquals failure pattern") {
            forAll(
                row(true, true, 1, true),
                row(false, false, 1, false),
            ) { orgStatus, filterStatus, amount, resultStats ->

                runTest {
                    var todo = Todo(null)
                    todo.task = "test"
                    todo.status = orgStatus
                    todoRepository.save(todo)

                    var result = todoRepository.findAllByStatusEquals(filterStatus, PageRequest.of(0, 20))
                    result.first().status shouldBe resultStats
                    result.count() shouldBe amount
                }
            }
        }
    }
}
