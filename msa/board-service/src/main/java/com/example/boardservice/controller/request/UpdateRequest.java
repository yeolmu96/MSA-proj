package com.example.boardservice.controller.request;


import com.example.boardservice.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequest {
    private Long id;
    private String title;
    private String content;
    private Category category;
}
