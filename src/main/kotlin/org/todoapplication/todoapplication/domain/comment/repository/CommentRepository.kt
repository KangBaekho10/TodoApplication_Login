package org.todoapplication.todoapplication.domain.comment.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.todoapplication.todoapplication.domain.comment.model.Comment

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByTodoCardTodoIdAndCommentId(todoId: Long, commentId: Long): Comment?
}