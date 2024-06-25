package org.todoapplication.todoapplication.domain.todo.todocard.repository

import com.querydsl.core.BooleanBuilder
import org.todoapplication.todoapplication.domain.todo.todocard.model.QTodoCard
import org.todoapplication.todoapplication.domain.todo.todocard.model.TodoCard
import org.todoapplication.todoapplication.infra.querydsl.QueryDslSupport
import java.time.LocalDateTime

class TodoCardRepositoryImpl : CustomTodoCardRepository, QueryDslSupport() {
    private val todoCard = QTodoCard.todoCard

    override fun search(searchCondition: Map<String, String>): List<TodoCard> {
        val builder = allCond(searchCondition)
        val query = queryFactory.selectFrom(todoCard).where(builder)
        return query.fetch()
    }

    private fun allCond(searchCondition: Map<String, String>): BooleanBuilder {
        val builder = BooleanBuilder()

        searchCondition.forEach { (key, value) ->
            when (key) {
                "title" -> builder.and(titleLike(value))
                "category" -> builder.and(categoryEq(value))
                "tag" -> builder.and(tagLike(value))
                "state" -> builder.and(stateEq(value))
                "daysAgo" -> builder.and(withInDays(value))
            }
        }
        return builder
    }

    private fun titleLike(title: String?): com.querydsl.core.types.dsl.BooleanExpression {
        return if (title.isNullOrBlank()) {
            todoCard.title.isNull
        } else {
            todoCard.title.containsIgnoreCase(title)
        }
    }

    private fun categoryEq(category: String?): com.querydsl.core.types.dsl.BooleanExpression {
        return if (category.isNullOrBlank()) {
            todoCard.category.isNull
        } else {
            todoCard.category.eq(category)
        }
    }

    private fun tagLike(tag: String?): com.querydsl.core.types.dsl.BooleanExpression {
        return if (tag.isNullOrBlank()) {
            todoCard.tag.isNull
        } else {
            todoCard.tag.containsIgnoreCase(tag)
        }
    }

    private fun stateEq(stateCode: String?): com.querydsl.core.types.dsl.BooleanExpression {
        return if (stateCode.isNullOrBlank()) {
            todoCard.state.isNull
        } else {
            todoCard.state.eq(stateCode)
        }
    }

    private fun withInDays(daysAgo: String?): com.querydsl.core.types.dsl.BooleanExpression {
        return if (daysAgo.isNullOrBlank()) {
            todoCard.date.isNull
        } else {
            val daysAgoInt = daysAgo.toIntOrNull() ?: return todoCard.date.isNull
            val date = LocalDateTime.now().minusDays(daysAgoInt.toLong())
            todoCard.date.after(date)
        }
    }
}