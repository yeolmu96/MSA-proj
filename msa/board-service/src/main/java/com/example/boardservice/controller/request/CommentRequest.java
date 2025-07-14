package com.example.boardservice.controller.request;

import lombok.Getter;

@Getter
public class CommentRequest {
    private String content;
    private Long postId;
}
