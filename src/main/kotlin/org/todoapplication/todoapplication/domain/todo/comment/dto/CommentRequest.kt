package org.todoapplication.todoapplication.domain.todo.comment.dto

data class CommentRequest(
    val writer: String,
    val content: String,
    val password: String,
)