package com.msa.account.service;

import com.msa.account.controller.response.ReviewAccountInfoResponse;
import com.msa.account.entity.Account;
import com.msa.account.redis_cache.RedisCacheService;
import com.msa.account.repository.AccountRepository;
import com.msa.account.utility.TokenUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final RedisCacheService redisCacheService;
    private final AccountRepository accountRepository;

    public ReviewAccountInfoResponse findAccountByToken(String token){
        String pureToken = TokenUtility.extractToken(token);

        Long accountId = redisCacheService.getValueByKey(pureToken, Long.class);

        if(accountId == null){
            throw new RuntimeException("인증이 필요합니다.");
        }

        Optional<Account> optionalAccount = accountRepository.findById(accountId);

        if(optionalAccount.isEmpty()){
            throw new RuntimeException("해당 사용자를 찾을 수 없습니다.");
        }

        Account account = optionalAccount.get();

        return new ReviewAccountInfoResponse(
                account.getNickname(),
                account.getTrainingId());
    }
}
