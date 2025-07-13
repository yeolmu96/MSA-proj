package com.msa.account.controller;


import com.msa.account.controller.request.RegisterAccountRequest;
import com.msa.account.controller.response.RegisterAccountResponse;
import com.msa.account.entitiy.Account;
import com.msa.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AcoountController {

    @Autowired
    private AccountRepository accountRepository;

    //사용자 등록
    @PostMapping("/register")
    public RegisterAccountResponse register(@RequestBody RegisterAccountRequest request) {
        Account requestedAccount = request.toAccount();
        Account createdAccount = accountRepository.save(requestedAccount);

        return RegisterAccountResponse.from(createdAccount);
    }
}
