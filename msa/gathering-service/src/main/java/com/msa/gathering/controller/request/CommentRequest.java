package com.msa.gathering.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    
    @NotNull(message = "모임 ID는 필수입니다.")
    private Long gatheringId;
    
    @NotNull(message = "계정 ID는 필수입니다.")
    private Long accountId;
    
    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Size(max = 500, message = "댓글은 최대 500자까지 입력 가능합니다.")
    private String content;
}
