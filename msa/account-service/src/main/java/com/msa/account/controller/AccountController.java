package com.msa.account.controller;


import com.msa.account.controller.request.LoginAccountRequest;
import com.msa.account.controller.request.RegisterAccountRequest;
import com.msa.account.controller.response.LoginAccountResponse;
import com.msa.account.controller.response.RegisterAccountResponse;
import com.msa.account.entity.Account;
import com.msa.account.repository.AccountRepository;
import com.msa.account.utility.EncryptionUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.msa.account.redis_cache.RedisCacheService;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RedisCacheService redisCacheService;

    @GetMapping("/test")
    public String test(){
        return "test success";
    }
    //사용자 등록
    @PostMapping("/register")
    public RegisterAccountResponse register(@RequestBody RegisterAccountRequest request) {

        Account requestedAccount = request.toAccount();
        Account createdAccount = accountRepository.save(requestedAccount);

        return RegisterAccountResponse.from(createdAccount);
    }

    //로그인
    @PostMapping("/login")
    public LoginAccountResponse login(@RequestBody LoginAccountRequest request){
        String requestedUserId = request.getUserId();
        Optional<Account> optionalAccount = accountRepository.findByUserId(requestedUserId);

        if (optionalAccount.isEmpty()) {
            return new LoginAccountResponse(null);
        }

        Account account = optionalAccount.get();
        String requestedPassword = request.getPassword();

        boolean matched = EncryptionUtility.matches(requestedPassword, account.getPassword());

        if(!matched){
            return new LoginAccountResponse(null);
        }

        String token = UUID.randomUUID().toString();
        redisCacheService.setKeyAndValue(token, account.getId(), Duration.ofDays(1));

        return LoginAccountResponse.from(token);
    }
}
