package com.msa.reviewservice.controller.response;

import com.msa.reviewservice.entity.Review;

import java.time.LocalDateTime;

public class CreateReviewResponse {
    private Long id;

    private String nickname;
    private String company;
    private String title;
    private String content;
    private Integer rating;
    private LocalDateTime createdAt;

    public CreateReviewResponse() {}

    public CreateReviewResponse(Long id, String nickname, String company, String title, String content, Integer rating, LocalDateTime createdAt) {
        this.id = id;

        this.nickname = nickname;
        this.company = company;
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    public static CreateReviewResponse from(Review review) {
        return new CreateReviewResponse(review.getId(), review.getNickname(), review.getCompany(), review.getTitle(), review.getContent(), review.getRating(), review.getCreatedAt());
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCompany() {
        return company;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Integer getRating() {
        return rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
