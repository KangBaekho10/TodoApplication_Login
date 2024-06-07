package org.todoapplication.todoapplication.domain.user.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.todoapplication.todoapplication.domain.user.dto.LoginRequest
import org.todoapplication.todoapplication.domain.user.dto.LoginResponse
import org.todoapplication.todoapplication.domain.user.dto.UserRequest
import org.todoapplication.todoapplication.domain.user.dto.UserResponse
import org.todoapplication.todoapplication.domain.user.service.UserService

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
    fun login (@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.login(request))
    }
}