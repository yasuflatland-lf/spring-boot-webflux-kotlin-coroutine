package com.sennproject.springbootwebfluxkotlincoroutine.models

import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

/**
 * @author Yasuyuki Takeo
 */
@NoArgsConstructor
@Table("todos")
data class Todo(
    @Id
    var id: Long?,
    var task: String? = "",
    var status: Boolean? = false,
    val created_at: LocalDateTime = LocalDateTime.now(),
    var updated_at: LocalDateTime = LocalDateTime.now()
)