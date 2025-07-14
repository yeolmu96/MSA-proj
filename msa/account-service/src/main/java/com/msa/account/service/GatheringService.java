package com.msa.account.service;

import com.msa.account.controller.request.GatheringAccountRequest;
import com.msa.account.controller.response.AccountInfoResponse;
import com.msa.account.entity.Account;
import com.msa.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        String nickname = accountRepository.findById(req.getAccountId())
                .map(Account::getNickname)
                .orElse("Unknown");

        return new AccountInfoResponse(
                req.getAccountId(),
                req.getGatheringId(),
                nickname,
                req.getRole(),
                req.isHost()
        );
    }
}
