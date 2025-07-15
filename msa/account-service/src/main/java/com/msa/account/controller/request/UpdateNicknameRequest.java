package com.msa.account.controller.request;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter @Service
public class UpdateNicknameRequest {
    private String newNickname;
}
