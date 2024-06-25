package org.todoapplication.todoapplication.domain.todocard.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.todoapplication.todoapplication.domain.exception.ModelNotFoundException
import org.todoapplication.todoapplication.domain.todo.todocard.repository.TodoCardRepository
import org.todoapplication.todoapplication.domain.todo.todocard.repository.TodoCardRepositoryImpl
import org.todoapplication.todoapplication.domain.todo.todocard.service.TodoCardServiceImpl

@SpringBootTest
@ExtendWith(MockKExtension::class)
class CourseServiceTest : BehaviorSpec({
    extension(SpringExtension)

    afterContainer {
        clearAllMocks()
    }

    val todoCardRepository = mockk<TodoCardRepository>()
    val todoCardRepositoryImpl = mockk<TodoCardRepositoryImpl>()

    val todoCardService = TodoCardServiceImpl(todoCardRepository, todoCardRepositoryImpl)

    Given("TodoCard가 존재하지 않을때") {
        When("특정 TodoCard를 요청하면") {
            Then("ModelNotFoundException이 발생해야 한다.") {
                val todoId = 1L
                every { todoCardRepository.findByIdOrNull(any()) } returns null

                shouldThrow<ModelNotFoundException> {
                    todoCardService.getTodoCardById(todoId)
                }
            }
        }
    }
})