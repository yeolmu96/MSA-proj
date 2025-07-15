package com.msa.gathering.service;

import com.msa.gathering.controller.request.GatheringApplicationApproveRequest;
import com.msa.gathering.controller.request.GatheringApplicationRequest;
import com.msa.gathering.entity.*;
import com.msa.gathering.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
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
    private final GatheringTechStackRepository gatheringTechStackRepository;
    private final GatheringMemberRepository gatheringMemberRepository;
    private final GatheringMemberTechStackRepository gatheringMemberTechStackRepository;
    private final TechStackRepository techStackRepository;





    @Transactional
    @Override
    public void application(GatheringApplicationRequest req) {

        try {
            gatheringApplicationRepository.save(GatheringApplication.from(req));
            Gathering gathering = gatheringRepository.findById(req.getGatheringId())
                    .orElseThrow(() -> new RuntimeException("모임 찾는데 오류 발생"));

            log.info("리퀘스트 게더 아이디 : {}", req.getGatheringId());
            log.info("찾아진 게더 아이디 : {}", gathering.getId());

            req.getTechStacks().forEach(stack -> {
                gatheringTechStackRepository.save(new GatheringTechStack(
                        gathering,
                        techStackRepository.findById(stack).get()));
            });
        } catch (DataAccessException e) {
            throw new RuntimeException("모임 신청에서 오류 발생 ", e);
        }
    }

    @Override
    public void approve(GatheringApplicationApproveRequest req) {
        GatheringApplication gatheringApplication = gatheringApplicationRepository.findById(req.getApplicationId())
                .orElseThrow(() -> new RuntimeException("신청 확인에서 오류 발생"));
        Gathering foundGathering = gatheringRepository.findById(gatheringApplication.getGatheringId())
                .orElseThrow(() -> new RuntimeException("모임을 찾는중 오류 발생 "));


        if(req.getApproved()){
            gatheringApplication.setApproved(Application.APPROVED);
            GatheringMember gatheringMember = new GatheringMember(
                    gatheringApplication.getAccountId(),
                    gatheringApplication.getRole(),
                    foundGathering,
                    false
            );
            gatheringMemberRepository.save(gatheringMember);
            List<GatheringMemberTechStack> gatheringMemberTechStack = new ArrayList<>();
            req.getTechStacks().forEach(t ->{
                gatheringMemberTechStack.add(new GatheringMemberTechStack(
                        gatheringMember,
                        techStackRepository.findById(t).get()));

            });

            for (GatheringMemberTechStack memberTechStack : gatheringMemberTechStack) {
                gatheringMemberTechStackRepository.save(memberTechStack);
            }

        } else{
            gatheringApplication.setApproved(Application.REJECTED);
        }

    }
}
