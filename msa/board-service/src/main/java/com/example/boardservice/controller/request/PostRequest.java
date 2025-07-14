package com.example.boardservice.controller.request;

import com.example.boardservice.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {
    private String title;
    private String content;
    private Long boardId;
    private Long view_count;
    private Category category;
}
