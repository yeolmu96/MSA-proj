package com.msa.account.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NicknameService {

    //닉네임 랜덤으로 생성
    public String generateRandomNickname() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
