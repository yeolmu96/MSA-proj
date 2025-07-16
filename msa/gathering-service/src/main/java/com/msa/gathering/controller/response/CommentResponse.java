package com.msa.gathering.controller.response;

import com.msa.gathering.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private Long gatheringId;
    private Long accountId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .gatheringId(comment.getGathering().getId())
                .accountId(comment.getAccountId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
