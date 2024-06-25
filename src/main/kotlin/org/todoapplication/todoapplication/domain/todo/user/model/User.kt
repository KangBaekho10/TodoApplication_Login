package org.todoapplication.todoapplication.domain.todo.user.model

import jakarta.persistence.*
import org.todoapplication.todoapplication.domain.todo.user.dto.UserResponse

@Entity
@Table(name = "users")
class User(
    val email: String,
    val password: String,
    val nickname: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun User.toResponse(): UserResponse {
    return UserResponse(
        id = id?: throw IllegalStateException("UserId should not be null"),
        nickname = nickname,
        email = email,
    )
}