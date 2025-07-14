package com.msa.account.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginAccountRequest {

    private Long accountId;
    private String password;

    public LoginAccountRequest(){

    }

    public LoginAccountRequest(Long accountId, String password) {
        this.accountId = accountId;
        this.password = password;
    }
}
