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

    private Long accountId;
    private String nickname;
    private String institutionType;

    public ReviewAccountInfoResponse(Long accountId, String nickname, String institutionType) {
        this.accountId = accountId;
        this.nickname = nickname;
        this.institutionType = institutionType;
    }
}
