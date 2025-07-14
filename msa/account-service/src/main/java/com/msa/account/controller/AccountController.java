package com.msa.account.controller;


import com.msa.account.controller.request.GatheringAccountRequest;
import com.msa.account.controller.request.LoginAccountRequest;
import com.msa.account.controller.request.RegisterAccountRequest;
import com.msa.account.controller.response.*;
import com.msa.account.entity.Account;
import com.msa.account.repository.AccountRepository;
import com.msa.account.service.GatheringService;
import com.msa.account.service.MyAccountService;
import com.msa.account.service.ReviewService;
import com.msa.account.service.TokenService;
import com.msa.account.utility.EncryptionUtility;
import com.msa.account.utility.TokenUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.msa.account.redis_cache.RedisCacheService;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RedisCacheService redisCacheService;
    private final GatheringService gatheringService;
    private final ReviewService reviewService;
    private final MyAccountService myAccountService;
    private final TokenService tokenService;

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

    //로그인(userToken 반환)
    @PostMapping("/login")
    public LoginAccountResponse login(@RequestBody LoginAccountRequest request){
        String userId = request.getUserId();
        Optional<Account> optionalAccount = accountRepository.findByUserId(userId);

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

    //Gathering 요청
    @PostMapping("/gathering-info")
    public List<AccountInfoResponse> GatheringAccountRequest(@RequestBody List<GatheringAccountRequest> request){
        List<AccountInfoResponse> accountInfo = gatheringService.getAccountInfo(request);
        log.info("GatheringAccountRequest:{}",accountInfo.toString());
        return accountInfo;
    }

    //로그인해서 발급 받은 토큰으로 현재 로그인한 계정의 ID를 확인
    @GetMapping("/find-id")
    public ResponseEntity<IdAccountResponse> getAccountId(@RequestHeader("Authorization") String token){

        String pureToken = TokenUtility.extractToken(token);
        Long accountId = redisCacheService.getValueByKey(pureToken, Long.class);

        if(accountId == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Account> account = accountRepository.findById(accountId);

        if(account.isEmpty()){
            throw new RuntimeException("사용자가 존재하지 않음");
        }

        return ResponseEntity.ok(IdAccountResponse.from(account.get().getId()));
    }

    //로그아웃(redis delete)
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token){
        String pureToken = TokenUtility.extractToken(token);
        redisCacheService.deleteKey(pureToken);

        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    //Review 요청
    @GetMapping("/review-info")
    public ReviewAccountInfoResponse getAccountInfo(@RequestHeader("Authorization") String token){
        return reviewService.findAccountByToken(token);
    }

    //내 정보 조회
    @GetMapping("/me")
    public MyAccountInfoResponse getMyAccountInfo(@RequestHeader("Authorization") String token){
        return myAccountService.getMyAccountInfo(token);
    }

    //새 토큰 발급
    @PostMapping("/refresh-token")
    public LoginAccountResponse refreshToken(@RequestHeader("Authorization") String token){
        String newToken = tokenService.refreshToken(token);
        return LoginAccountResponse.from(newToken);
    }
}
