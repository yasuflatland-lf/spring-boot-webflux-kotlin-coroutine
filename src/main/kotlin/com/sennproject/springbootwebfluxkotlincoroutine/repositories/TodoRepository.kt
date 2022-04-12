package com.sennproject.springbootwebfluxkotlincoroutine.repositories

import com.sennproject.springbootwebfluxkotlincoroutine.models.Todo
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Service

@Service
interface TodoRepository : CoroutineCrudRepository<Todo, Long> {
    public fun findAllByStatus(status: Boolean, paging: Pageable?): Flow<Todo>
}