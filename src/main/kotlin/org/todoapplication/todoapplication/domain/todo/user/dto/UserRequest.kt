package org.todoapplication.todoapplication.domain.todo.user.dto

data class UserRequest(
    val email: String,
    val password: String,
    val nickname: String,
)

