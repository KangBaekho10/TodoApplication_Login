package org.todoapplication.todoapplication.domain.todocard.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.todoapplication.todoapplication.domain.exception.ModelNotFoundException
import org.todoapplication.todoapplication.domain.todocard.dto.CreateTodoCardRequest
import org.todoapplication.todoapplication.domain.todocard.dto.TodoCardResponse
import org.todoapplication.todoapplication.domain.todocard.dto.UpdateTodoCardRequest
import org.todoapplication.todoapplication.domain.todocard.model.TodoCard
import org.todoapplication.todoapplication.domain.todocard.model.toResponse
import org.todoapplication.todoapplication.domain.todocard.repository.TodoCardRepository


@Service
class TodoCardServiceImpl(
    private val todoCardRepository: TodoCardRepository,
) : TodoCardService {

    override fun getAllTodoCardList(pageNo: Int, count: Int): Page<TodoCardResponse> {
        val pageable: Pageable = PageRequest.of(pageNo, count)
        return todoCardRepository.findAllBy(pageable).map { it.toResponse() }
    }

    override fun getTodoCardById(todoId: Long): TodoCardResponse {
        val todoCard = todoCardRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("TodoCard", todoId)
        return todoCard.toResponse()
    }

    @Transactional
    override fun createTodoCard(request: CreateTodoCardRequest): TodoCardResponse {
        return todoCardRepository.save(
            TodoCard(
                writer = request.writer,
                title = request.title,
                content = request.content,
                date = request.date,
            )
        ).toResponse()
    }

    @Transactional
    override fun updateTodoCard(todoId: Long, request: UpdateTodoCardRequest): TodoCardResponse {
        val todoCard = todoCardRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("TodoCard", todoId)
        val (writer, title, content) = request

        todoCard.writer = writer
        todoCard.title = title
        todoCard.content = content

        return todoCardRepository.save(todoCard).toResponse()
    }

    @Transactional
    override fun deleteTodoCard(todoId: Long) {
        val todoCard = todoCardRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("TodoCard", todoId)
        todoCardRepository.delete(todoCard)
    }
}