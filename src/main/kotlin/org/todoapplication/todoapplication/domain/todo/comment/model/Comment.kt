package org.todoapplication.todoapplication.domain.todo.comment.model

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import org.todoapplication.todoapplication.domain.todo.comment.dto.CommentResponse
import org.todoapplication.todoapplication.domain.todo.todocard.model.TodoCard

@Entity
@Table(name = "comment")
class Comment(
    @Column
    @NotNull
    var content: String,
    var writer: String,
    var password: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    val todoCard: TodoCard,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var commentId: Long? = null
}

fun Comment.toResponse(): CommentResponse {
    return CommentResponse(
        commentId = commentId ?: throw IllegalStateException("commentId should not be null"),
        content = content,
        writer = writer,
    )
}