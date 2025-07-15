package com.msa.account.controller.response;

import com.msa.account.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class RegisterAccountResponse {

    private String userId;
    private String nickname;
    private String company;
    private Long point;

    public RegisterAccountResponse() {

    }

    public static RegisterAccountResponse from(Account account){
        return new RegisterAccountResponse(
                account.getUserId(),
                account.getNickname(),
                account.getCompany(),
                account.getPoint()
        );
    }
}
