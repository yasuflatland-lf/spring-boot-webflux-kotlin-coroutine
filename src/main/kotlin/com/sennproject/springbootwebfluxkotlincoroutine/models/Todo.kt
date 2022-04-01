package com.sennproject.springbootwebfluxkotlincoroutine.models

import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@NoArgsConstructor
@Table("todos")
data class Todo(
    @Id
    val id: Long? = 0L,
    val task: String? = "",
    val done: Boolean? = false,
)