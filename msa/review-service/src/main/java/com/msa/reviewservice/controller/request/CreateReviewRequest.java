package com.msa.reviewservice.controller.request;

import com.msa.reviewservice.controller.response.NcsType;
import com.msa.reviewservice.controller.response.ReviewAccountInfoResponse;
import com.msa.reviewservice.entity.Review;
import lombok.*;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewRequest {
    @NotNull
    private String title;
    @NotNull
    private String content;

    @NotNull(message = "별점을 입력해 주세요.")
    @Min(value = 1, message = "1~5의 숫자만 입력 가능합니다.")
    @Max(value = 5, message = "1~5의 숫자만 입력 가능합니다.")
    private Integer rating;

    public Review toReview(Long accountId, String nickname,Long trainingId, String trainingName, int trainingPeriod, NcsType trainingType, String imagePath) {
        return new Review(accountId, nickname, trainingId, trainingName, trainingPeriod, trainingType, title, content, rating, imagePath);
    }
}
