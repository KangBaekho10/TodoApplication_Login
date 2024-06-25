package org.todoapplication.todoapplication.domain.todo.todocard.repository

import org.todoapplication.todoapplication.domain.todo.todocard.model.TodoCard

interface CustomTodoCardRepository {

    fun search(searchCondition: Map<String, String>): List<TodoCard>
}