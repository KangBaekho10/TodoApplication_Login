package org.todoapplication.todoapplication.domain.user.dto

data class UserResponse(
    val id: Long,
    val email: String,
    val nickname: String,
)
