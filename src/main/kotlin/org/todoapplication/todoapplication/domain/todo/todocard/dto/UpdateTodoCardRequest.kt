package org.todoapplication.todoapplication.domain.todo.todocard.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateTodoCardRequest(
    @field:NotBlank
    val writer: String,

    @field:NotBlank
    @field:Size(min = 1, max = 200)
    val title: String,

    @field:NotBlank
    @field:Size(min = 1, max = 1000)
    val content: String,

    val category: String,
    val tag: String,
    val state: String,
    val completed: Boolean,
)