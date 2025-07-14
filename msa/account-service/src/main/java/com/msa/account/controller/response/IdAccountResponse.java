package com.msa.account.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IdAccountResponse {

    private Long accountId;

    public IdAccountResponse() {

    }

    public IdAccountResponse(Long accountId) {
        this.accountId = accountId;
    }

    public static IdAccountResponse from(Long accountId) {
        return new IdAccountResponse(accountId);
    }
}
