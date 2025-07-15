package com.msa.reviewservice.controller.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateReviewRequest {
    @NotNull
    private Long reviewId;
    @NotNull
    private String title;
    @NotNull
    private String content;

    @NotNull(message = "별점을 입력해 주세요.")
    @Min(value = 1, message = "1~5의 숫자만 입력 가능합니다.")
    @Max(value = 5, message = "1~5의 숫자만 입력 가능합니다.")
    private Integer rating;

    public UpdateReviewRequest() {}

    public UpdateReviewRequest(String title, String content, Integer rating) {
        this.title = title;
        this.content = content;
        this.rating = rating;
    }


}
