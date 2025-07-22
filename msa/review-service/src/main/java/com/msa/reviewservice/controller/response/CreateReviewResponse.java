package com.msa.reviewservice.controller.response;

import com.msa.reviewservice.entity.Review;

import java.time.LocalDateTime;

public class CreateReviewResponse {
    private Long id;

    private String nickname;
    private String trainingName;
    private int trainingPeriod;
    private NcsType trainingType;
    private String title;
    private String content;
    private Integer rating;
    private LocalDateTime createdAt;
    private String imagePath;

    public CreateReviewResponse() {}

    public CreateReviewResponse(Long id, String nickname, String trainigName, int trainingPeriod, NcsType trainingType, String title, String content, Integer rating, LocalDateTime createdAt, String imagePath) {
        this.id = id;

        this.nickname = nickname;
        this.trainingName = trainigName;
        this.trainingPeriod = trainingPeriod;
        this.trainingType = trainingType;
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.createdAt = createdAt;
        this.imagePath = imagePath;
    }

    public static CreateReviewResponse from(Review review) {
        return new CreateReviewResponse(review.getId(), review.getNickname(), review.getTrainingName(), review.getTrainingPeriod(), review.getTrainingType(), review.getTitle(), review.getContent(), review.getRating(), review.getCreatedAt(), review.getImagePath());
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public int getTrainingPeriod() { return trainingPeriod; }

    public NcsType getTrainingType() { return trainingType; }

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
