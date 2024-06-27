package org.todoapplication.todoapplication.domain.todo.user.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.todoapplication.todoapplication.domain.todo.user.dto.*
import org.todoapplication.todoapplication.domain.todo.user.service.UserService

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/signup")
    fun signup(@RequestBody request: UserRequest): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.signup(request))
    }

    @PostMapping("/login")
    fun login(@RequestBody request: UserRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.login(request))
    }

    @PostMapping("/password-check")
    fun passwordCheck(
        @RequestBody request: PasswordRequest
    ): ResponseEntity<Unit> {
        userService.passwordCheck(request)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @PostMapping("/check-nickname")
    fun checkNickname(@RequestBody request: NicknameRequest): ResponseEntity<Map<String, Boolean>> {
        val isNicknameAvailable = userService.isNicknameAvailable(request.nickname)
        val response = mapOf("available" to isNicknameAvailable)
        return ResponseEntity.ok(response)
    }
}