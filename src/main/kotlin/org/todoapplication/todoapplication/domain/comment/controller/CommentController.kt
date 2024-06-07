package org.todoapplication.todoapplication.domain.comment.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.todoapplication.todoapplication.domain.comment.dto.CommentRequest
import org.todoapplication.todoapplication.domain.comment.dto.CommentResponse
import org.todoapplication.todoapplication.domain.comment.dto.DeleteCommentRequest
import org.todoapplication.todoapplication.domain.comment.service.CommentService
import org.todoapplication.todoapplication.domain.user.service.UserService

@RequestMapping("/todocards/{todoId}")
@RestController
class CommentController(
    private val commentService: CommentService,
    private val userService: UserService,
) {
    @PostMapping("/comments")
    fun createComment(
        @PathVariable todoId: Long,
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<CommentRequest> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentRequest)
    } // 생성

    @PutMapping("/comments/{commentId}")
    fun updateComment(
        @PathVariable todoId: Long,
        @PathVariable commentId: Long,
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(todoId, commentId, commentRequest))
    } // 수정

    @DeleteMapping("/comments/{commentId}")
    fun deleteComment(
        @PathVariable todoId: Long,
        @PathVariable commentId: Long,
        @RequestBody deleteCommentRequest: DeleteCommentRequest
    ): ResponseEntity<Unit> {
        commentService.deleteComment(todoId, commentId, deleteCommentRequest)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    } // 삭제
}