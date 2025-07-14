package com.msa.account.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginAccountRequest {

    private String userId;
    private String password;

    public LoginAccountRequest(){

    }

    public LoginAccountRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
