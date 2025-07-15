package com.msa.reviewservice.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdAccountResponse {

    private Long accountId;

    public IdAccountResponse() {}

    public IdAccountResponse(Long accountId) {
        this.accountId = accountId;
    }
}
