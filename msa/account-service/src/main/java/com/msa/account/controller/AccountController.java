package com.msa.account.controller;


import com.msa.account.controller.request.*;
import com.msa.account.controller.response.*;
import com.msa.account.entity.Account;
import com.msa.account.repository.AccountRepository;
import com.msa.account.service.*;
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
import java.util.*;

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
    private final MyAccountService myAccountService;
    private final TokenService tokenService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/test")
    public String test(){
        return "test success";
    }

    //사용자 등록
    @PostMapping("/register")
    public RegisterAccountResponse register(@RequestBody RegisterAccountRequest request) {
        Account created = accountService.register(request);
        return RegisterAccountResponse.from(created);
    }

    //로그인(userToken 반환)
    @PostMapping("/login")
    public LoginAccountResponse login(@RequestBody LoginAccountRequest request){
        String userId = request.getUserId();
        Optional<Account> optionalAccount = accountRepository.findByUserId(userId);

        if (optionalAccount.isEmpty()) {
            accountService.logActivity(null, "LOGIN_FAIL", "아이디 없음");
            return new LoginAccountResponse(null);
        }

        Account account = optionalAccount.get();
        String requestedPassword = request.getPassword();

        boolean matched = EncryptionUtility.matches(requestedPassword, account.getPassword());

        if(!matched){
            accountService.logActivity(account.getId(), "LOGIN_FAIL", "비밀번호 불일치");
            return new LoginAccountResponse(null);
        }

        //로그인 성공
        String token = UUID.randomUUID().toString();
        redisCacheService.setKeyAndValue(token, account.getId(), Duration.ofDays(1));
        accountService.logActivity(account.getId(), "LOGIN", "로그인 성공");

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
        Long accountId = redisCacheService.getValueByKey(pureToken, Long.class);

        redisCacheService.deleteKey(pureToken);

        if(accountId != null){
            accountService.logActivity(accountId, "LOGOUT", "로그아웃 완료");
        }

        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    //Review 요청
    @GetMapping("/review-info")
    public ReviewAccountInfoResponse getAccountInfo(@RequestParam("accountId") Long accountId){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("해당 계정을 찾을 수 없습니다. Id: " +  accountId));

        return new ReviewAccountInfoResponse(account.getNickname(), account.getTrainingId());
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

    //비밀번호 변경
    @PatchMapping("/password")
    public ResponseEntity<String> changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody PasswordChangeRequest request
    ){
        accountService.changePassword(token, request.getCurrentPassword(), request.getNewPassword());
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }

    //닉네임 변경+포인트 차감
    @PatchMapping("/nickname")
    public ResponseEntity<String> updateNickname(
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateNicknameRequest request
    ){
        accountService.updateNicknameAndDeductPoint(token, request.getNewNickname());
        return ResponseEntity.ok("닉네임이 변경되었고 포인트가 100 차감되었습니다.");
    }

    //교육 기관명 수정
    @PatchMapping("/training")
    public ResponseEntity<Map<String, Long>> updateTraining(
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateTrainingIdRequest request
    ){
        Long updatedTrainingId = accountService.updateTrainingId(token, request.getNewTrainingId());

        Map<String, Long> response = new HashMap<>();
        response.put("trainingId", Long.valueOf(updatedTrainingId));

        return ResponseEntity.ok(response);
    }

    //포인트 조회
    @GetMapping("/point")
    public ResponseEntity<Map<String, Long>> getPoint(
            @RequestHeader("Authorization") String token
    ){
        Long point = accountService.getPoint(token);
        Map<String, Long> response = new HashMap<>();
        response.put("point", point);

        return ResponseEntity.ok(response);
    }

    //포인트 증감
    @PostMapping("/update-point")
    public ResponseEntity<UpdatePointResponse> updatePoint(@RequestBody UpdatePointRequest request){
        UpdatePointResponse response = accountService.updatePoint(request);
        return ResponseEntity.ok(response);
    }

    //회원탈퇴
    @DeleteMapping("/account-delete")
    public ResponseEntity<String> deleteAccount(@RequestHeader("Authorization") String token){
        String pureToken = TokenUtility.extractToken(token);
        Long accountId = redisCacheService.getValueByKey(pureToken, Long.class);
        accountService.deleteAccount(token);

        if(accountId != null){
            accountService.logActivity(accountId, "DELETE", "계정 삭제 완료");
        }

        return ResponseEntity.ok("계정이 완전히 삭제되었습니다.");
    }
}
