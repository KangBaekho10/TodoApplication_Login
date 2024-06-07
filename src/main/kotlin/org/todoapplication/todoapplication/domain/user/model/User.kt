package org.todoapplication.todoapplication.domain.user.model

import jakarta.persistence.*
import org.todoapplication.todoapplication.domain.comment.model.Comment
import org.todoapplication.todoapplication.domain.todocard.model.TodoCard
import org.todoapplication.todoapplication.domain.user.dto.UserResponse

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
        id = id!!,
        nickname = nickname,
        email = email,
    )
}