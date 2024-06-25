package org.todoapplication.todoapplication.domain.todo.comment.service

import org.todoapplication.todoapplication.domain.todo.comment.dto.CommentResponse
import org.todoapplication.todoapplication.domain.todo.comment.dto.CommentRequest
import org.todoapplication.todoapplication.domain.todo.comment.dto.DeleteCommentRequest

interface CommentService {
    // 댓글 조회
    fun getComment(commentId : Long) : CommentResponse

    // 댓글 생성
    fun createComment(todoId: Long, request: CommentRequest) : CommentResponse

    // 댓글 수정
    fun updateComment(todoId: Long, commentId: Long, request: CommentRequest) : CommentResponse

    // 댓글 삭제
    fun deleteComment(todoId: Long, commentId: Long, request: DeleteCommentRequest)
}