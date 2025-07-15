package com.msa.gathering.service;

import com.msa.gathering.controller.request.GatheringApplicationApproveRequest;
import com.msa.gathering.controller.request.GatheringApplicationRequest;
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

            return true;

    }

    @Transactional
    @Override
    public void approve(GatheringApplicationApproveRequest req) {
        GatheringApplication gatheringApplication = gatheringApplicationRepository.findById(req.getApplicationId())
                .orElseThrow(() -> new RuntimeException("신청 확인에서 오류 발생 By approve"));
        Gathering foundGathering = gatheringRepository.findById(gatheringApplication.getGatheringId())
                .orElseThrow(() -> new RuntimeException("모임을 찾는중 오류 발생 By approve"));

        foundGathering.setCurrentMemberCount(foundGathering.getCurrentMemberCount()+1);


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

    }
}
