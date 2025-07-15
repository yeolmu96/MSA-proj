package com.example.boardservice.entity;

import lombok.Getter;

@Getter
public enum Category {
    FREE("자유게시판"),
    COURSE("훈련과정"),
    LIFE("일상공유"),
    MIND("정신치료·심리상담"),
    EMPLOYMENT("채용해요"),
    COMPANYLIFE("회사생활"),
    INTEREST("취미생활"),
    TRAVEL("여행"),
    SPORT("스포츠");

    private final String description;

    Category(String description) {
        this.description = description;
    }
}
