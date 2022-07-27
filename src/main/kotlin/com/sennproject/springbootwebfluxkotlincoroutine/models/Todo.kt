package com.sennproject.springbootwebfluxkotlincoroutine.models

import io.swagger.v3.oas.annotations.media.Schema
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
    @Schema(example = "null")
    var id: Long? = null,

    @Schema(example = "Buy a milk")
    var task: String? = "",

    @Schema(example = "false")
    var status: Boolean? = false,

    val created_at: LocalDateTime = LocalDateTime.now(),
    var updated_at: LocalDateTime = LocalDateTime.now()
)