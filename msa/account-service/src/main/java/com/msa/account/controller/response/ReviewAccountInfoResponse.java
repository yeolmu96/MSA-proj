package com.msa.account.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/*
Review 응답
 */
@Getter @Setter
@NoArgsConstructor
@ToString
public class ReviewAccountInfoResponse {

    private String nickname;
    private Long trainingId;

    public ReviewAccountInfoResponse(String nickname, Long trainingId) {
        this.nickname = nickname;
        this.trainingId = trainingId;
    }
}
