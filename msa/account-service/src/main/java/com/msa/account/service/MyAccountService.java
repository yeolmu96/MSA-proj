package com.msa.account.service;

import com.msa.account.controller.response.MyAccountInfoResponse;
import com.msa.account.entity.Account;
import com.msa.account.redis_cache.RedisCacheService;
import com.msa.account.repository.AccountRepository;
import com.msa.account.utility.TokenUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyAccountService {

    private final RedisCacheService redisCacheService;
    private final AccountRepository accountRepository;

    public MyAccountInfoResponse getMyAccountInfo(String token) {
        String pureToken = TokenUtility.extractToken(token);
        Long accountId = redisCacheService.getValueByKey(pureToken, Long.class);

        if(accountId == null){
            throw new RuntimeException("인증이 필요합니다.");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return new MyAccountInfoResponse(
                account.getId(),
                account.getUserId(),
                account.getNickname(),
                account.getTrainingId(),
                account.getPoint(),
                account.getCreatedAt()
        );
    }
}
