package org.todoapplication.todoapplication.domain.todo.comment.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.todoapplication.todoapplication.domain.todo.comment.dto.CommentResponse
import org.todoapplication.todoapplication.domain.todo.comment.dto.CommentRequest
import org.todoapplication.todoapplication.domain.todo.comment.dto.DeleteCommentRequest
import org.todoapplication.todoapplication.domain.todo.comment.model.Comment
import org.todoapplication.todoapplication.domain.todo.comment.model.toResponse
import org.todoapplication.todoapplication.domain.todo.comment.repository.CommentRepository
import org.todoapplication.todoapplication.domain.exception.ModelNotFoundException
import org.todoapplication.todoapplication.domain.exception.IllegalArgumentException
import org.todoapplication.todoapplication.domain.todo.todocard.repository.TodoCardRepository

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val todoCardRepository: TodoCardRepository
) : CommentService {
    override fun getComment(commentId: Long): CommentResponse {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        return comment.toResponse()
    }

    @Transactional
    override fun createComment(todoId: Long, request: CommentRequest): CommentResponse {
        val todoCard = todoCardRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("TodoCard", todoId)
        return commentRepository.save(
            Comment(
                writer = request.writer,
                content = request.content,
                password = request.password,
                todoCard = todoCard,
            )
        ).toResponse()
    }

    @Transactional
    override fun updateComment(todoId: Long, commentId: Long, request: CommentRequest): CommentResponse {
        val comment = commentRepository.findByTodoCardTodoIdAndCommentId(todoId, commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (comment.writer == request.writer && comment.password == request.password) {

            comment.content = request.content

            return commentRepository.save(comment).toResponse()
        } else {
            throw IllegalArgumentException()
        }
    }

    @Transactional
    override fun deleteComment(todoId: Long, commentId: Long, request: DeleteCommentRequest) {
        val comment = commentRepository.findByTodoCardTodoIdAndCommentId(todoId, commentId) ?: throw ModelNotFoundException("Comment", commentId)
        if (comment.writer == request.writer && comment.password == request.password) {
            return commentRepository.delete(comment)
        } else {
            throw IllegalArgumentException()
        }
    }
}