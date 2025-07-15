package com.msa.reviewservice.controller.response;

import lombok.*;

@Getter
@Setter
@ToString
public class ReviewAccountInfoResponse {
    private String nickname;
    private Long trainingId;

    public ReviewAccountInfoResponse() {}

    public ReviewAccountInfoResponse(String nickname, Long trainingId) {
        this.nickname = nickname;
        this.trainingId = trainingId;
    }

}
