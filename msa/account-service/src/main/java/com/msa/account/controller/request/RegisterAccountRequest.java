package com.msa.account.controller.request;

import com.msa.account.entity.Account;
import com.msa.account.utility.EncryptionUtility;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
public class RegisterAccountRequest {

    private String userId;
    private String password;
    private String nickname;
    @NotNull
    private Long trainingId;

    public RegisterAccountRequest() {

    }

    public RegisterAccountRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public RegisterAccountRequest(String userId, String password, String nickname) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
    }

    public RegisterAccountRequest(String userId, String password, String nickname, Long trainingId) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.trainingId = trainingId;
    }
}
