package com.msa.gathering.controller;

import com.msa.gathering.controller.request.CommentRequest;
import com.msa.gathering.controller.response.CommentResponse;
import com.msa.gathering.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     */
    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest request) {
        CommentResponse response = commentService.addComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 모임의 모든 댓글 조회
     */
    @GetMapping("/gathering/{gatheringId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByGathering(@PathVariable Long gatheringId) {
        List<CommentResponse> comments = commentService.getCommentsByGathering(gatheringId);
        return ResponseEntity.ok(comments);
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long commentId,
            @RequestParam Long accountId,
            @RequestParam  String content) {
        CommentResponse response = commentService.updateComment(commentId, accountId, content);
        return ResponseEntity.ok(response);
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @RequestParam Long accountId) {
        boolean deleted = commentService.deleteComment(commentId, accountId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
