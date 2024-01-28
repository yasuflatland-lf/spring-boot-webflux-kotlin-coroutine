package com.sennproject.springbootwebfluxkotlincoroutine.repositories

import com.sennproject.springbootwebfluxkotlincoroutine.models.Todo
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Service

/**
 * @author Yasuyuki Takeo
 */
@Service
interface TodoRepository : CoroutineCrudRepository<Todo, Long> {
    fun findAllByStatusEquals(status: Boolean, paging: Pageable?) : Flow<Todo>
}