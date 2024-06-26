package org.todoapplication.todoapplication.domain.todo.user.service

import org.todoapplication.todoapplication.domain.todo.user.dto.LoginResponse
import org.todoapplication.todoapplication.domain.todo.user.dto.PasswordRequest
import org.todoapplication.todoapplication.domain.todo.user.dto.UserRequest
import org.todoapplication.todoapplication.domain.todo.user.dto.UserResponse

interface UserService {
    // 회원가입
    fun signup(request: UserRequest): UserResponse

    // 로그인
    fun login(request: UserRequest): LoginResponse

    fun passwordCheck(request: PasswordRequest)

    fun isNicknameAvailable(nickname: String): Boolean
}