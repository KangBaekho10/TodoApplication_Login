package org.todoapplication.todoapplication.domain.user.dto

data class LoginRequest(
    val email : String,
    val password : String,
)
