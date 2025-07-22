package com.msa.account.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyAccountInfoResponse {
    private Long accountId;
    private String userId;
    private String nickname;
    private Long trainingId;
    private Long point;
    private LocalDateTime createdAt;
}
