package com.msa.account.service;

import com.msa.account.redis_cache.RedisCacheService;
import com.msa.account.repository.AccountRepository;
import com.msa.account.utility.TokenUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisCacheService redisCacheService;
    private final AccountRepository accountRepository;

    public String refreshToken(String token) {
        String pureToken = TokenUtility.extractToken(token);

        //기존 토큰에서 accountId 가져오기
        Long accountId = redisCacheService.getValueByKey(pureToken, Long.class);

        if(accountId == null){
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }

        //기존 토큰 삭제
        redisCacheService.deleteKey(pureToken);

        //새 토큰 생성
        String newToken = UUID.randomUUID().toString();

        //새 토큰 Redis에 저장
        redisCacheService.setKeyAndValue(newToken, accountId, Duration.ofDays(1));

        return newToken;
    }
}
