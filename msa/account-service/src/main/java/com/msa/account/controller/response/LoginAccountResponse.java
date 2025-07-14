package com.msa.account.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginAccountResponse {

    private String userToken;

    public LoginAccountResponse() {

    }

    public LoginAccountResponse(String userToken) {
        this.userToken = userToken;
    }

    public static LoginAccountResponse from(String userToken) {
        return new LoginAccountResponse(userToken);
    }
}
