package org.todoapplication.todoapplication.domain.todo.todocard.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class CreateTodoCardRequest(
    @field:NotBlank
    val writer: String,

    @field:NotBlank
    @field:Size(min = 1, max = 500)
    val title: String,

    @field:NotBlank
    @field:Size(min = 1, max = 5000)
    val content: String,

    val category: String,
    val tag: String,
    val state: String,
    val date: LocalDateTime
)