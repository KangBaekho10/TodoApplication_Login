package org.todoapplication.todoapplication.domain.user.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.todoapplication.todoapplication.domain.exception.ModelNotFoundException
import org.todoapplication.todoapplication.domain.user.dto.LoginRequest
import org.todoapplication.todoapplication.domain.user.dto.LoginResponse
import org.todoapplication.todoapplication.domain.user.dto.UserRequest
import org.todoapplication.todoapplication.domain.user.dto.UserResponse
import org.todoapplication.todoapplication.domain.user.exception.InvalidCredentialException
import org.todoapplication.todoapplication.domain.user.model.User
import org.todoapplication.todoapplication.domain.user.model.toResponse
import org.todoapplication.todoapplication.domain.user.repository.UserRepository
import org.todoapplication.todoapplication.infra.security.jwt.JwtPlugin

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) {
    fun signup(request: UserRequest): UserResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalStateException("Email is already in use")
        }

        return userRepository.save(
            User(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                nickname = request.nickname,
            )
        ).toResponse()
    }

    fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(request.email) ?: throw ModelNotFoundException("User", null)

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw InvalidCredentialException()
        }

        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                email = user.email,
            )
        )
    }
}