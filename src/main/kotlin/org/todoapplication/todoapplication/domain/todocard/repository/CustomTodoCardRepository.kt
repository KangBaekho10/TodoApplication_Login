package org.todoapplication.todoapplication.domain.todocard.repository

import org.todoapplication.todoapplication.domain.todocard.model.TodoCard

interface CustomTodoCardRepository {

    fun searchTodoCardListByTitle(title: String): List<TodoCard>
}