package com.msa.account.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdatePointResponse {
    private Long accountId;
    private Long updatePoint; //갱신된 총 포인트
    private String message; //처리 결과 메시지

    public UpdatePointResponse(Long accountId, Long updatePoint, String message) {
        this.accountId = accountId;
        this.updatePoint = updatePoint;
        this.message = message;
    }
}
