package org.todoapplication.todoapplication.domain.todocard.controller

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.todoapplication.todoapplication.domain.todocard.dto.TodoCardResponse
import org.todoapplication.todoapplication.domain.todocard.model.TodoCardCompleted
import org.todoapplication.todoapplication.domain.todocard.service.TodoCardService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
class TodoCardControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
) : DescribeSpec({
    extension(SpringExtension)

    afterContainer {
        clearAllMocks()
    }
    val todocardService = mockk<TodoCardService>()

    describe("TodoCardController의 특정 TodoCard 조회") {
        context("존재하는 ID를 요청할 때") {
            it("200 status code를 응답") {
                val date = LocalDateTime.now()
                val todoId = 1L

                every { todocardService.getTodoCardById(any()) } returns TodoCardResponse(
                    todoId = todoId,
                    writer = "test writer",
                    title = "test title",
                    content = "test content",
                    date = date,
                    completed = TodoCardCompleted.FALSE,
                    comments = mutableListOf(),
                )

                val result = mockMvc.perform(
                    get("/todocards/{todoId}", todoId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                ).andReturn()

                result.response.status shouldBe 200

                val DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSS"

                val responseDto = jacksonObjectMapper().registerModule(
                    JavaTimeModule().addSerializer(
                        LocalDateTime::class.java, LocalDateTimeSerializer(
                            DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)
                        )
                    )
                ).readValue(
                    result.response.contentAsString,
                    TodoCardResponse::class.java
                )

                responseDto.todoId shouldBe todoId
            }
        }
    }
})