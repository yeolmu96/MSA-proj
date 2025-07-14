package com.msa.account.controller.response;

import com.msa.account.entity.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterAccountResponse {

    private Boolean isRegisterSuccess;

    public RegisterAccountResponse() {

    }

    public RegisterAccountResponse(Boolean isRegisterSuccess) {
        this.isRegisterSuccess = isRegisterSuccess;
    }

    public static RegisterAccountResponse from(Account account) {
        return new RegisterAccountResponse(account != null);
    }
}
