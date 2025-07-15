package com.msa.reviewservice.controller.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ReviewListResponse {

    private String title;
    private Integer rating;


    public ReviewListResponse(Integer rating, String title) {
        this.rating = rating;
        this.title = title;
    }
}
