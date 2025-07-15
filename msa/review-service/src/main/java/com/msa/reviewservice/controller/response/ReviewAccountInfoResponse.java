package com.msa.reviewservice.controller.response;

import lombok.*;

@Getter
@Setter
@ToString
public class ReviewAccountInfoResponse {
    private String nickname;
    private String institutionType;

    public ReviewAccountInfoResponse() {}

    public ReviewAccountInfoResponse(String nickname, String institutionType) {
        this.nickname = nickname;
        this.institutionType = institutionType;
    }

}
