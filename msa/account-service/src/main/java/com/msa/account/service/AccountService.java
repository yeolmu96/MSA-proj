package com.msa.account.service;

import com.msa.account.client.InstitutionClient;
import com.msa.account.controller.request.RegisterAccountRequest;
import com.msa.account.entity.Account;
import com.msa.account.exception.DuplicateUserIdException;
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
    private final NicknameService nicknameService;
    private final InstitutionClient institutionClient;

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

    //교육 기관 코드 수정
    @Transactional
    public Long updateTrainingId(String token, Long newTrainingId) {
        String pureToken = TokenUtility.extractToken(token);

        Long accountId = redisCacheService.getValueByKey(pureToken, Long.class);
        if(accountId == null){
            throw new RuntimeException("인증이 필요합니다.");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        account.setTrainingId(newTrainingId);

        return account.getTrainingId();
    }

    //포인트 조회
    @Transactional(readOnly = true)
    public Long getPoint(String token) {
        String pureToken = TokenUtility.extractToken(token);

        Long accountId = redisCacheService.getValueByKey(pureToken, Long.class);
        if(accountId == null){
            throw new RuntimeException("인증이 필요합니다.");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return account.getPoint();
    }

    //회원가입(userId 중복 검사, 비밀번호 정책 검증, 닉네임 처리)
    public Account register(RegisterAccountRequest request){
        //교육 코드 null 검사
        if(request.getTrainingId() == null){
            throw new IllegalArgumentException("교육 코드를 반드시 입력해야 합니다.");
        }

        //교육 코드 존재 여부 검증
        boolean exist = institutionClient.checkInstitutionExists(request.getTrainingId());
        if(!exist){
            throw new IllegalArgumentException("존재하지 않는 교육 코드입니다.");
        }

        //userId 중복 검사
        if(accountRepository.existsByUserId(request.getUserId())){
            throw new DuplicateUserIdException("이미 사용 중인 아이디입니다.");
        }

        //비밀전호 정책 검증
        PasswordPolicyValidator.validate(request.getPassword());

        //닉네임 처리
        String nickname = (request.getNickname() == null || request.getNickname().isBlank())
                ? nicknameService.generateRandomNickname()
                : request.getNickname();

        //Account 엔티티 생성 + 비밀번호 암호화
        Account account = new Account(
                request.getUserId(),
                EncryptionUtility.encode(request.getPassword()),
                nickname,
                request.getTrainingId(),
                500L, // 초기 포인트 지급
                null // 생성일자는 @CreationTimestamp로 자동 생성
        );

        //저장
        return accountRepository.save(account);
    }
}
