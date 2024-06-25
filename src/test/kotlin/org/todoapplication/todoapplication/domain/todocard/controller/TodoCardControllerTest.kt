package org.todoapplication.todoapplication.domain.todocard.controller

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.todoapplication.todoapplication.domain.todo.todocard.dto.TodoCardResponse
import org.todoapplication.todoapplication.domain.todo.todocard.model.TodoCardCompleted
import org.todoapplication.todoapplication.domain.todo.todocard.service.TodoCardService
import org.todoapplication.todoapplication.infra.security.jwt.JwtPlugin
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExtendWith(MockKExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class TodoCardControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val jwtPlugin: JwtPlugin,
    @MockkBean private val todoCardService: TodoCardService,
) : DescribeSpec({

    afterContainer {
        clearAllMocks()
    }

    describe("TodoCardController의 특정 TodoCard 조회") {
        context("존재하는 ID를 요청할 때") {
            it("200 status code를 응답") {
                val todoId = 1L

                every { todoCardService.getTodoCardById(any<Long>()) } returns TodoCardResponse(
                    todoId = todoId,
                    writer = "test writer",
                    title = "test title",
                    content = "test content",
                    date = LocalDateTime.now(),
                    completed = TodoCardCompleted.FALSE,
                    category = "test category",
                    tag = "test tag",
                    state = "test state",
                    comments = mutableListOf(),
                )

                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "test@gmail.com",
                )

                val result = mockMvc.perform(
                    get("/todocards/{todoId}", todoId.toString())
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
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