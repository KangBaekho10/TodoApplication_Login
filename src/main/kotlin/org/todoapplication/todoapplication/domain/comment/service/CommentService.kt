package org.todoapplication.todoapplication.domain.comment.service

import org.todoapplication.todoapplication.domain.comment.dto.CommentResponse
import org.todoapplication.todoapplication.domain.comment.dto.CommentRequest
import org.todoapplication.todoapplication.domain.comment.dto.DeleteCommentRequest

interface CommentService {
    fun getComment(commentId : Long) : CommentResponse

    fun createComment(todoId: Long, request: CommentRequest) : CommentResponse

    fun updateComment(todoId: Long, commentId: Long, request: CommentRequest) : CommentResponse

    fun deleteComment(todoId: Long, commentId: Long, request: DeleteCommentRequest)
}