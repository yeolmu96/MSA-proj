package com.msa.account.service;

import com.msa.account.entity.Account;
import com.msa.account.redis_cache.RedisCacheService;
import com.msa.account.repository.AccountRepository;
import com.msa.account.utility.EncryptionUtility;
import com.msa.account.utility.PasswordPolicyValidator;
import com.msa.account.utility.TokenUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final RedisCacheService redisCacheService;
    private final AccountRepository accountRepository;

    //비밀번호 변경
    public void changePassword(String token, String currentPassword, String newPassword) {

        String pureToken = TokenUtility.extractToken(token);

        Long accountId = redisCacheService.getValueByKey(pureToken, Long.class);

        if(accountId == null){
            //토큰이 잘못되거나 만료된 경우
            throw new RuntimeException("인증이 필요합니다.");
        }

        Account account = accountRepository.findById(accountId)
                //사용자가 없는 경우
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        boolean matched = EncryptionUtility.matches(currentPassword, account.getPassword());

        if(!matched){
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }

        //정책 검증
        PasswordPolicyValidator.validate(newPassword);

        //암호화 후 저장
        String encrypted = EncryptionUtility.encode(newPassword);
        account.setPassword(encrypted);
        accountRepository.save(account);
    }

    //닉네임 변경
    @Transactional
    public void updateNicknameAndDeductPoint(String token, String newNickname) {
        String pureToken = TokenUtility.extractToken(token);

        //Redis에서 accountId 가져오기
        Long accountId = redisCacheService.getValueByKey(pureToken, Long.class);
        if(accountId == null){
            throw new RuntimeException("인증이 필요합니다.");
        }

        //DB에서 Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        //포인트 차감 검증
        if(account.getPoint() < 100){
            throw new RuntimeException("포인트가 부족하여 닉네임을 변경할 수 없습니다.");
        }

        //포인트 차감
        account.setPoint(account.getPoint() - 100);

        //닉네임 변경
        account.setNickname(newNickname);
    }
}
