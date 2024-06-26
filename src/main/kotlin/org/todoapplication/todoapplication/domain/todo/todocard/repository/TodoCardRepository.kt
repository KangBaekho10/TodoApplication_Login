package org.todoapplication.todoapplication.domain.todo.todocard.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.todoapplication.todoapplication.domain.todo.todocard.model.TodoCard

interface TodoCardRepository : JpaRepository<TodoCard, Long>, CustomTodoCardRepository {
    fun findAllBy(pageable: Pageable): Page<TodoCard>
}