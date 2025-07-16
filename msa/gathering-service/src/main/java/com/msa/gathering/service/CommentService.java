package com.msa.gathering.service;

import com.msa.gathering.controller.request.CommentRequest;
import com.msa.gathering.controller.response.CommentResponse;

import java.util.List;

public interface CommentService {
    
    /**
     * 모임에 댓글 작성
     * @param request 댓글 요청 정보
     * @return 작성된 댓글 정보
     */
    CommentResponse addComment(CommentRequest request);
    
    /**
     * 모임의 모든 댓글 조회
     * @param gatheringId 모임 ID
     * @return 댓글 목록
     */
    List<CommentResponse> getCommentsByGathering(Long gatheringId);
    
    /**
     * 댓글 수정
     * @param commentId 댓글 ID
     * @param accountId 계정 ID (작성자 확인용)
     * @param content 수정할 내용
     * @return 수정된 댓글 정보
     */
    CommentResponse updateComment(Long commentId, Long accountId, String content);
    
    /**
     * 댓글 삭제
     * @param commentId 댓글 ID
     * @param accountId 계정 ID (작성자 확인용)
     * @return 삭제 성공 여부
     */
    boolean deleteComment(Long commentId, Long accountId);
}
