package org.todoapplication.todoapplication.domain.todo.todocard.model

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import org.todoapplication.todoapplication.domain.todo.comment.model.Comment
import org.todoapplication.todoapplication.domain.todo.comment.model.toResponse
import org.todoapplication.todoapplication.domain.todo.todocard.dto.TodoCardResponse
import java.time.LocalDateTime

@Entity
@Table(name = "card")
class TodoCard(
    @Column
    @NotNull
    var writer: String,
    var title: String,
    val category: String,
    val tag: String,
    val state: String,
    var content: String,
    var date: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @Column
    @NotNull
    var completed: TodoCardCompleted = TodoCardCompleted.FALSE,

    @OneToMany(mappedBy = "todoCard", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val comment: MutableList<Comment> = mutableListOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var todoId: Long? = null
}

fun TodoCard.toResponse(): TodoCardResponse {
    return TodoCardResponse(
        todoId = todoId ?: throw IllegalStateException("TodoId should not be null"),
        title = title,
        writer = writer,
        content = content,
        date = date,
        completed = completed,
        category = category,
        state = state,
        tag = tag,
        comments = comment.map { it.toResponse() }
    )
}