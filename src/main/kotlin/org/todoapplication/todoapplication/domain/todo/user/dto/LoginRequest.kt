package org.todoapplication.todoapplication.domain.todo.user.dto

data class LoginRequest(
    val email: String,
    val password: String,
)
