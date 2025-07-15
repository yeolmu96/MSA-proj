package com.msa.account.service;

import com.msa.account.controller.request.GatheringAccountRequest;
import com.msa.account.controller.response.AccountInfoResponse;
import com.msa.account.entity.Account;
import com.msa.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GatheringService {

    private final AccountRepository accountRepository;

    public List<AccountInfoResponse> getAccountInfo(List<GatheringAccountRequest> request) {
        return request.stream()
                .map(req -> toResponse(req))
                .collect(Collectors.toList());
    }

    private AccountInfoResponse toResponse(GatheringAccountRequest req) {

        log.info("GatheringAccountRequest:{}",req.toString());

        Account account = accountRepository.findById(req.getAccountId())
                .orElseThrow(() -> new RuntimeException("회원 못찾음"));

        String nickname = account.getNickname();

        log.info("nickname. :{}", nickname);
        log.info("account. :{}", account.toString());

        return new AccountInfoResponse(
                req.getAccountId(),
                req.getGatheringId(),
                nickname,
                req.getRole(),
                req.getIsHost()
        );
    }
}
