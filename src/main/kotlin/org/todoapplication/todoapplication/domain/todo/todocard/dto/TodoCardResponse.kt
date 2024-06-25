package org.todoapplication.todoapplication.domain.todo.todocard.dto

import org.todoapplication.todoapplication.domain.todo.comment.dto.CommentResponse
import org.todoapplication.todoapplication.domain.todo.todocard.model.TodoCardCompleted
import java.time.LocalDateTime

data class TodoCardResponse(
    val todoId: Long,
    val writer: String,
    val title: String,
    val content: String,
    val category: String,
    val tag: String,
    val state: String,
    val date: LocalDateTime,
    val completed: TodoCardCompleted,
    val comments: List<CommentResponse>,
)