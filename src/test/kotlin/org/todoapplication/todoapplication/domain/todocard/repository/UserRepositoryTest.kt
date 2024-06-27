package org.todoapplication.todoapplication.domain.todocard.repository

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.todoapplication.todoapplication.domain.todo.user.model.User
import org.todoapplication.todoapplication.domain.todo.user.repository.UserRepository

@DataJpaTest
@ExtendWith(SpringExtension::class)
class UserRepositoryTest {
    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    @DisplayName("회원 가입 테스트")
    fun dynamicInsertTest() {
        // given
        val newUser = User(email = "admin@admin.com", password = "password123", nickname = "Admin123")

        // when
        val saveUser = userRepository.save(newUser)

        // then
        Assertions.assertEquals("admin@admin.com", saveUser.email);
        Assertions.assertEquals("password123", saveUser.password);
        Assertions.assertEquals("Admin123", saveUser.nickname);
    }
}