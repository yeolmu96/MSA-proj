package com.msa.reviewservice.controller.response;

import com.msa.reviewservice.entity.Review;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UpdateReviewResponse {
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

    public UpdateReviewResponse() {}

    public UpdateReviewResponse(Long id, String nickname, String trainingName, int trainingPeriod, NcsType trainingType, String title, String content, Integer rating, LocalDateTime createdAt, String imagePath) {
        this.id = id;
        this.nickname = nickname;
        this.trainingName = trainingName;
        this.trainingPeriod = trainingPeriod;
        this.trainingType = trainingType;
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.createdAt = createdAt;
        this.imagePath = imagePath;
    }

    public static UpdateReviewResponse from(Review review) {
        return new UpdateReviewResponse(review.getId(), review.getNickname(), review.getTrainingName(), review.getTrainingPeriod(), review.getTrainingType(), review.getTitle(), review.getContent(), review.getRating(), review.getCreatedAt(), review.getImagePath());
    }
}
