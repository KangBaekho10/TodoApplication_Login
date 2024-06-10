package org.todoapplication.todoapplication.domain.todocard.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
class TodoCardTest : StringSpec({
    "TODO Entity 확인" {
        val date = LocalDateTime.now()
        val todoId = 123456789
        val todoCard = TodoCard(
            title = "Test title",
            writer = "Test writer",
            content = "Test content",
            date = date,
            completed = TodoCardCompleted.FALSE,
        )

        todoCard.todoId shouldBe todoId
        todoCard.title shouldBe "Test title"
        todoCard.writer shouldBe "Test writer"
        todoCard.content shouldBe "Test content"
        todoCard.date shouldBe date
        todoCard.completed shouldBe TodoCardCompleted.FALSE

        val response = todoCard.toResponse()
        response.todoId shouldBe todoId
        response.title shouldBe "Test title"
        response.writer shouldBe "Test writer"
        response.content shouldBe "Test content"
        response.date shouldBe date
        response.completed shouldBe TodoCardCompleted.FALSE
    }
})