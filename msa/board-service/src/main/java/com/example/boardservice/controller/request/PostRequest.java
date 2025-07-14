package com.example.boardservice.controller.request;

import lombok.Getter;

@Getter
public class PostRequest {
    private String title;
    private String content;
    private Long boardId;
    private Long view_count;
}
