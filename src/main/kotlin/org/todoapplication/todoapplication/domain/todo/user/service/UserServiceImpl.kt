package org.todoapplication.todoapplication.domain.todo.user.service

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.todoapplication.todoapplication.domain.exception.IllegalStateException
import org.todoapplication.todoapplication.domain.exception.InvalidCredentialException
import org.todoapplication.todoapplication.domain.exception.ModelNotFoundException
import org.todoapplication.todoapplication.domain.todo.user.dto.LoginResponse
import org.todoapplication.todoapplication.domain.todo.user.dto.PasswordRequest
import org.todoapplication.todoapplication.domain.todo.user.dto.UserRequest
import org.todoapplication.todoapplication.domain.todo.user.dto.UserResponse
import org.todoapplication.todoapplication.domain.todo.user.model.User
import org.todoapplication.todoapplication.domain.todo.user.model.toResponse
import org.todoapplication.todoapplication.domain.todo.user.repository.UserRepository
import org.todoapplication.todoapplication.infra.security.UserPrincipal
import org.todoapplication.todoapplication.infra.security.jwt.JwtPlugin

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) : UserService {
    override fun signup(request: UserRequest): UserResponse {

        val nicknameLower = request.nickname.lowercase()
        val passwordLower = request.password.lowercase()

        if (userRepository.existsByEmail(request.email)) {
            throw IllegalStateException("중복된 이메일입니다.")
        }
        if (request.nickname.length < 3) {
            throw IllegalStateException("닉네임은 최소 3자 이상 필요합니다.")
        } else if (nicknameLower.contains(passwordLower)) {
            throw IllegalStateException("닉네임에 비밀번호가 포함될 수 없습니다.")
        } else if (userRepository.existsByNickname(request.nickname)) {
            throw IllegalStateException("중복된 닉네임입니다.")
        } else if (!isValidNickname(request.nickname)) {
            throw IllegalStateException("닉네임에 최소 4자 이상 필요하고, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 합니다.")
        }
        return userRepository.save(
            User(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                nickname = request.nickname,
            )
        ).toResponse()
    }

    override fun login(request: UserRequest): LoginResponse {
        val user = userRepository.findByEmail(request.email) ?: throw ModelNotFoundException("User", null)

        if (request.nickname != user.nickname || !passwordEncoder.matches(request.password, user.password)) {
            throw InvalidCredentialException()
        }

        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                email = user.email,
            )
        )
    }

    override fun passwordCheck(request: PasswordRequest) {
        val userId = getUserIdFromToken()

        val user = userRepository.findById(userId) ?: throw ModelNotFoundException("User", null)

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalStateException("비밀번호가 일치하지 않습니다.")
        }
    }

    override fun isNicknameAvailable(nickname: String): Boolean {
        val alreadyUsed = userRepository.findByNickname(nickname)
        if (!isValidNickname(nickname)) {
            throw IllegalStateException("닉네임에 최소 4자 이상 필요하고, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 합니다.")
        } else if (userRepository.existsByNickname(nickname)) {
            throw IllegalStateException("중복된 닉네임입니다.")
        }
        return alreadyUsed == null
    }

    fun getUserIdFromToken(): Long? {
        val principal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        return principal.id
    }

    fun isValidNickname(nickname: String): Boolean {
        val regex = Regex("^(=*[a-zA-Z0-9]){4,}\$")
        return regex.matches(nickname)
    }
}