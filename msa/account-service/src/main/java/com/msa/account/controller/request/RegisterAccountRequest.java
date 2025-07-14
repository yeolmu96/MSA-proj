package com.msa.account.controller.request;

import com.msa.account.entity.Account;
import com.msa.account.utility.EncryptionUtility;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterAccountRequest {

    private String userId;
    private String password;

    public RegisterAccountRequest() {

    }

    public RegisterAccountRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public Account toAccount() {
        return new Account(userId, EncryptionUtility.encode(password));
    }
}
