package org.todoapplication.todoapplication.domain.todocard.service

import org.springframework.data.domain.Page
import org.todoapplication.todoapplication.domain.todocard.dto.CreateTodoCardRequest
import org.todoapplication.todoapplication.domain.todocard.dto.TodoCardResponse
import org.todoapplication.todoapplication.domain.todocard.dto.UpdateTodoCardRequest

interface TodoCardService {
    fun getAllTodoCardList(pageNo: Int, count: Int): Page<TodoCardResponse>

    fun getTodoCardById(todoId: Long): TodoCardResponse

    fun createTodoCard(request: CreateTodoCardRequest): TodoCardResponse

    fun updateTodoCard(todoId: Long, request: UpdateTodoCardRequest): TodoCardResponse

    fun deleteTodoCard(todoId: Long)
}