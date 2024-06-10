package org.todoapplication.todoapplication.domain.todocard.repository

import org.todoapplication.todoapplication.domain.todocard.model.QTodoCard
import org.todoapplication.todoapplication.domain.todocard.model.TodoCard
import org.todoapplication.todoapplication.infra.querydsl.QueryDslSupport

class TodoCardRepositoryImpl: CustomTodoCardRepository, QueryDslSupport() {
    private val todoCard = QTodoCard.todoCard

    override fun searchTodoCardListByTitle(title: String): List<TodoCard> {
        return queryFactory.selectFrom(todoCard)
            .where(todoCard.title.containsIgnoreCase(title))
            .fetch()
    }
}