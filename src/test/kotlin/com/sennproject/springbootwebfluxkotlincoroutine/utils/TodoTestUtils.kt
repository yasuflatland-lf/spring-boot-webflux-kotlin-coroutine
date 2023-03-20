package com.sennproject.springbootwebfluxkotlincoroutine.utils

import com.sennproject.springbootwebfluxkotlincoroutine.models.Todo
import net.datafaker.Faker

class TodoTestUtils {
    companion object {
        suspend fun createRandomList(amount: Long, trueAmount: Int): MutableList<Todo> {
            var result = mutableListOf<Todo>()
            val faker = Faker()
            var countAmount = trueAmount.toLong()

            for (i in 1..amount) {
                var todo = Todo(null)
                todo.task = faker.starTrek().character()
                todo.status = 0 < countAmount--
                result.add(todo)
            }
            return result
        }
    }
}