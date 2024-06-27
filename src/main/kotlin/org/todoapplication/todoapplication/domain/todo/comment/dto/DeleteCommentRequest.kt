package org.todoapplication.todoapplication.domain.todo.comment.dto

data class DeleteCommentRequest(
    val writer: String,
    val password: String,
)