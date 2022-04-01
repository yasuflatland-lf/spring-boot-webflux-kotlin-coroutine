package com.sennproject.springbootwebfluxkotlincoroutine.repositories

import com.sennproject.springbootwebfluxkotlincoroutine.AbstractContainerBaseTest
import com.sennproject.springbootwebfluxkotlincoroutine.models.Todo
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = [AbstractContainerBaseTest.Initializer::class])
class TodoRepositoryTest : FunSpec() {
    override fun extensions() = listOf(SpringExtension)

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
    }
}
