package com.msa.gathering.service;

import com.msa.gathering.client.AccountClient;
import com.msa.gathering.controller.request.GatheringAccountRequest;
import com.msa.gathering.controller.request.GatheringApplicationApproveRequest;
import com.msa.gathering.controller.request.GatheringApplicationRequest;
import com.msa.gathering.controller.response.AccountInfoResponse;
import com.msa.gathering.entity.*;
import com.msa.gathering.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class GatheringApplicationServiceImpl implements GatheringApplicationService {

    private final GatheringApplicationRepository gatheringApplicationRepository;
    private final GatheringRepository gatheringRepository;
    private final GatheringApplicationTechStackRepository gatheringApplicationTechStackRepository;
    private final GatheringMemberRepository gatheringMemberRepository;
    private final GatheringMemberTechStackRepository gatheringMemberTechStackRepository;
    private final TechStackRepository techStackRepository;
    private final NotificationService notificationService;
    private final AccountClient accountClient;



    @Transactional(readOnly = true)
    @Override
    public boolean applicationNumberCheck(GatheringApplicationRequest gatheringApplicationRequest) {
        Long gatheringId = gatheringApplicationRequest.getGatheringId();
        Gathering gathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다. By applicationNumberCheck"));
        if(gathering.getCurrentMemberCount()>= gathering.getMaxMemberCount()) {
            return false;
        }
        return true;

    }


    @Transactional
    @Override
    public boolean application(GatheringApplicationRequest req) {
        GatheringApplication gatheringApplication = GatheringApplication.from(req);
        List<GatheringApplication> gatheringApplications = gatheringApplicationRepository.findByAccountId(req.getAccountId());

        if(!gatheringApplications.isEmpty()) {
            return false;
        }

        // 저장 후 ID 생성
        gatheringApplicationRepository.save(gatheringApplication);
        
        Gathering gathering = gatheringRepository.findById(req.getGatheringId())
                .orElseThrow(() -> new RuntimeException("모임 찾는데 오류 발생"));

        log.info("리퀘스트 게더 아이디 : {}", req.getGatheringId());
        log.info("찾아진 게더 아이디 : {}", gathering.getId());

        req.getTechStacks().forEach(stack -> {
            TechStack techStack = techStackRepository.findById(stack).get();
            log.info("기술 스택 : {}",techStack.getTechStackName());
            gatheringApplicationTechStackRepository.save(new GatheringApplicationTechStack(
                    gatheringApplication,
                    techStack
            ));
        });
        
        // 신청자 정보 가져오기
        GatheringAccountRequest accountRequest = new GatheringAccountRequest(
                req.getAccountId(),
                req.getGatheringId(),
                req.getRole(),
                false
        );
        
        List<AccountInfoResponse> accountInfos = accountClient.getList(List.of(accountRequest));
        String applicantName = "알 수 없음";
        if (!accountInfos.isEmpty()) {
            applicantName = accountInfos.get(0).getNickname();
        }
        
        // 모임 호스트에게 알림 생성
        notificationService.createApplicationNotification(
                gathering.getHostId(),
                gathering.getId(),
                gatheringApplication.getId(),
                applicantName
        );

        return true;
    }

    @Transactional
    @Override
    public void approve(GatheringApplicationApproveRequest req) {
        GatheringApplication gatheringApplication = gatheringApplicationRepository.findById(req.getApplicationId())
                .orElseThrow(() -> new RuntimeException("신청 확인에서 오류 발생 By approve"));
        Gathering foundGathering = gatheringRepository.findById(gatheringApplication.getGatheringId())
                .orElseThrow(() -> new RuntimeException("모임을 찾는중 오류 발생 By approve"));

        // 승인된 경우에만 현재 멤버 수 증가
        if(req.getApproved()) {
            foundGathering.setCurrentMemberCount(foundGathering.getCurrentMemberCount()+1);
        }

        if(req.getApproved()){
            gatheringApplication.setApproved(Application.APPROVED);
            GatheringMember gatheringMember = new GatheringMember(
                    gatheringApplication.getAccountId(),
                    gatheringApplication.getRole(),
                    foundGathering,
                    false
            );
            gatheringApplicationRepository.save(gatheringApplication);
            gatheringMemberRepository.save(gatheringMember);
            List<GatheringApplicationTechStack> gatheringApplicationTechStack =
                    gatheringApplicationTechStackRepository.findByGatheringApplicationId(req.getApplicationId());

            List<GatheringMemberTechStack> gatheringMemberTechStack = new ArrayList<>();
            gatheringApplicationTechStack.forEach(t ->{
                gatheringMemberTechStack.add(new GatheringMemberTechStack(
                        gatheringMember,
                        t.getTechStack()));

            });

            for (GatheringMemberTechStack memberTechStack : gatheringMemberTechStack) {
                gatheringMemberTechStackRepository.save(memberTechStack);
            }

        } else{
            gatheringApplication.setApproved(Application.REJECTED);
            gatheringApplicationRepository.save(gatheringApplication);
        }
        
        // 신청자에게 승인/거절 알림 생성
        notificationService.createApplicationStatusNotification(
                gatheringApplication.getAccountId(),
                gatheringApplication.getGatheringId(),
                gatheringApplication.getId(),
                req.getApproved()
        );
    }
}
