package com.msa.account.service;

import com.msa.account.client.InstitutionClient;
import com.msa.account.controller.request.RegisterAccountRequest;
import com.msa.account.controller.request.UpdatePointRequest;
import com.msa.account.controller.response.UpdatePointResponse;
import com.msa.account.entity.Account;
import com.msa.account.entity.AccountActivityLog;
import com.msa.account.exception.DuplicateUserIdException;
import com.msa.account.redis_cache.RedisCacheService;
import com.msa.account.repository.AccountActivityLogRepository;
import com.msa.account.repository.AccountRepository;
import com.msa.account.utility.EncryptionUtility;
import com.msa.account.utility.PasswordPolicyValidator;
import com.msa.account.utility.PointPolicy;
import com.msa.account.utility.TokenUtility;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final RedisCacheService redisCacheService;
    private final AccountRepository accountRepository;
    private final NicknameService nicknameService;

    @Autowired
    private AccountActivityLogRepository accountActivityLogRepository;

    //로그 남기기
    public void logActivity(Long accountId, String activityType, String description){
        AccountActivityLog log = new AccountActivityLog();
        log.setAccountId(accountId);
        log.setActivityType(activityType);
        log.setDescription(description);
        accountActivityLogRepository.save(log);
    }

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

    //포인트 증감
    @Transactional
    public UpdatePointResponse updatePoint(UpdatePointRequest request){

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("계정을 찾을 수 없습니다."));

        Long beforePoint = account.getPoint() == null ? 0 : account.getPoint();
        int pointChange;

        switch (request.getReason()) {
            case REVIEW_WRITE:
                pointChange = PointPolicy.REVIEW_WRITE;
                break;
            case REVIEW_READ:
                pointChange = PointPolicy.REVIEW_READ;
                break;
            case GATHERING_CREATE:
                pointChange = PointPolicy.GATHERING_CREATE;
                break;
            case POST_RECOMMEND:
                pointChange = PointPolicy.POST_RECOMMEND;
                break;
            case QNA_ANSWER:
                pointChange = PointPolicy.QNA_ANSWER;
                break;
            case NICKNAME_CHANGE:
                pointChange = PointPolicy.NICKNAME_CHANGE;
                break;
            default:
                throw new IllegalArgumentException("포인트 사유가 올바르지 않습니다.");
        }

        Long afterPoint = beforePoint + pointChange;

        if(afterPoint < 0){
            throw new IllegalArgumentException("포인트가 부족합니다.");
        }

        account.setPoint(afterPoint);
        accountRepository.save(account);

        return new UpdatePointResponse(
                account.getId(),
                afterPoint,
                request.getReason() + " 처리 완료"
        );
    }

    //회원탈퇴(hard-delete)
    @Transactional
    public void deleteAccount(String token){
        String pureToken = TokenUtility.extractToken(token);

        Long accountId = redisCacheService.getValueByKey(pureToken, Long.class);
        if(accountId == null){
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }

        if(!accountRepository.existsById(accountId)){
            throw new IllegalArgumentException("계정을 찾을 수 없습니다.");
        }

        accountRepository.deleteById(accountId);
        redisCacheService.deleteKey(pureToken);
    }
}
