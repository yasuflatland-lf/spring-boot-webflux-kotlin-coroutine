package com.sennproject.springbootwebfluxkotlincoroutine.repositories

import com.sennproject.springbootwebfluxkotlincoroutine.models.Todo
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TodoRepository : CoroutineCrudRepository<Todo, Long>