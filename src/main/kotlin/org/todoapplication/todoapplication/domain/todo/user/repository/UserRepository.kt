package org.todoapplication.todoapplication.domain.todo.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.todoapplication.todoapplication.domain.todo.user.model.User

interface UserRepository : JpaRepository<User, Long> {
    fun existsByEmail(email: String): Boolean

    fun existsByNickname(email: String): Boolean

    fun findByEmail(email: String): User?

    fun findByNickname(nickname: String): User?

    fun findById(id: Long?): User
}