package com.msa.account.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/*
내 정보 조회 응답
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyAccountInfoResponse {
    private Long accountId;
    private String userId;
    private String nickname;
    private String company;
    private Long point;
    private LocalDateTime createdAt;
}
