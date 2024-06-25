package org.todoapplication.todoapplication.domain.todo.comment.dto

data class CommentResponse (
    val commentId: Long,
    val writer: String,
    val content: String,
)