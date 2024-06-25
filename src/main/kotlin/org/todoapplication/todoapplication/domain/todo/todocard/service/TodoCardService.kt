package org.todoapplication.todoapplication.domain.todo.todocard.service

import org.springframework.data.domain.Page
import org.todoapplication.todoapplication.domain.todo.todocard.dto.CreateTodoCardRequest
import org.todoapplication.todoapplication.domain.todo.todocard.dto.TodoCardResponse
import org.todoapplication.todoapplication.domain.todo.todocard.dto.UpdateTodoCardRequest
import org.todoapplication.todoapplication.domain.todo.todocard.model.TodoCard

interface TodoCardService {
    // 할 일 카드 전체 조회
    fun getAllTodoCardList(pageNo: Int, count: Int): Page<TodoCardResponse>

    // 할 일 카드 단건 조회
    fun getTodoCardById(todoId: Long): TodoCardResponse

    // 할 일 카드 검색 조회
    fun searchTodoCards(searchCondition: Map<String, String>): List<TodoCard>

    // 할 일 카드 생성
    fun createTodoCard(request: CreateTodoCardRequest): TodoCardResponse

    // 할 일 카드 수정
    fun updateTodoCard(todoId: Long, request: UpdateTodoCardRequest): TodoCardResponse

    // 할 일 카드 삭제
    fun deleteTodoCard(todoId: Long)
}