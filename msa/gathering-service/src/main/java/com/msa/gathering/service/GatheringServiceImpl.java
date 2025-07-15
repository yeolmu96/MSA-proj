package com.msa.gathering.service;

import com.msa.gathering.client.AccountClient;
import com.msa.gathering.controller.request.*;
import com.msa.gathering.controller.response.AccountInfoResponse;
import com.msa.gathering.controller.response.GatheringRoleStatusResponse;
import com.msa.gathering.controller.response.RoleResponse;
import com.msa.gathering.controller.response.TechStackResponse;
import com.msa.gathering.entity.*;
import com.msa.gathering.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GatheringServiceImpl implements GatheringService {

    private final AccountClient accountClient;
    private final GatheringRepository gatheringRepository;
    private final GatheringMemberRepository gatheringMemberRepository;
    private final GatheringApplicationRepository gatheringApplicationRepository;
    private final GatheringTechStackRepository gatheringTechStackRepository;
    private final TechStackRepository techStackRepository;
    private final GatheringRoleRepository gatheringRoleRepository;
    private final GatheringMemberTechStackRepository gatheringMemberTechStackRepository;
    private final GatheringApplicationTechStackRepository gatheringApplicationTechStackRepository;





    @Override
    public List<GatheringListRequest> getList() {
        List<Gathering> allGatherings = gatheringRepository.findAll();

        List<Long> gatheringIds = allGatherings.stream()
                .map(Gathering::getId)
                .toList();

        // Get all members for all gatherings
        List<GatheringMember> allMembers = gatheringMemberRepository.findByGatheringIdIn(gatheringIds);

        // Get all tech stacks for all gatherings
        List<GatheringTechStack> allTechStacks = gatheringTechStackRepository.findByGatheringIdIn(gatheringIds);
        Map<Long, List<GatheringTechStack>> techStacksByGatheringId = allTechStacks.stream()
                .collect(Collectors.groupingBy(ts -> ts.getGathering().getId()));
        
        // Get all roles for all gatherings
        List<GatheringRole> allRoles = gatheringRoleRepository.findByGatheringIdIn(gatheringIds);
        Map<Long, List<GatheringRole>> rolesByGatheringId = allRoles.stream()
                .collect(Collectors.groupingBy(r -> r.getGathering().getId()));
        
        List<GatheringAccountRequest> accountRequests = allMembers.stream()
                .map(m -> new GatheringAccountRequest(
                        m.getAccountId(),
                        m.getGathering().getId(),
                        m.getRole(),
                        m.isHost()))
                .toList();

        List<AccountInfoResponse> accountInfos = accountClient.getList(accountRequests);

        Map<Long, List<AccountInfoResponse>> groupedByGatheringId = accountInfos.stream()
                .collect(Collectors.groupingBy(AccountInfoResponse::getGatheringId));

        Map<Long, Gathering> gatheringMap = allGatherings.stream()
                .collect(Collectors.toMap(Gathering::getId, Function.identity()));

        List<GatheringListRequest> result = groupedByGatheringId.entrySet().stream()
                .map(entry -> {
                    Long gatheringId = entry.getKey();
                    List<AccountInfoResponse> members = entry.getValue();

                    Gathering g = gatheringMap.get(gatheringId);
                    if (g == null) throw new RuntimeException("Gathering not found");
                    
                    // Convert tech stacks to response objects
                    List<TechStackResponse> techStackResponses = techStacksByGatheringId.getOrDefault(gatheringId, List.of())
                            .stream()
                            .map(ts -> new TechStackResponse(ts.getTechStack()))
                            .toList();
                    
                    // Convert roles to response objects with status information
                    List<GatheringRoleStatusResponse> roleStatusResponses = rolesByGatheringId.getOrDefault(gatheringId, List.of())
                            .stream()
                            .map(GatheringRoleStatusResponse::new)
                            .toList();

                    return new GatheringListRequest(
                            gatheringId,
                            members,
                            g.getTeamName(),
                            g.getTitle(),
                            g.getMaxMemberCount(),
                            g.getCurrentMemberCount(),
                            g.getCreatedAt(),
                            techStackResponses,
                            roleStatusResponses
                    );
                })
                .toList();

        return result;
    }

    @Transactional
    @Override
    public void register(GatheringRegisterRequest registerRequest) {


        try {
            Gathering newGathering = new Gathering(registerRequest.getAccountId(), registerRequest.getTeamName(),
                    registerRequest.getTitle(), registerRequest.getDescription(), registerRequest.getMaxMemberCount(),
                    registerRequest.getCurrentMemberCount());

            gatheringRepository.save(newGathering);

            GatheringMember newGatheringMember = new GatheringMember(registerRequest.getAccountId(),
                    registerRequest.getRole(), newGathering, true);

            gatheringMemberRepository.save(newGatheringMember);

            registerRequest.getHostTechStacks().stream().forEach(techStack -> {
                TechStack tech = techStackRepository.findById(techStack)
                        .orElseThrow(() -> new RuntimeException("기술을 찾는 도중 오류 발생"));
                gatheringMemberTechStackRepository.save(new GatheringMemberTechStack(newGatheringMember, tech));

            });



            registerRequest.getRoleRequests().forEach(roleRequest -> {
                if(roleRequest.getRole().equals(roleRequest.getRole())) {
                    gatheringRoleRepository.save(new GatheringRole(newGathering, roleRequest.getRole(), roleRequest.getRequiredNumber(),1));
                }
                else{
                    gatheringRoleRepository.save(new GatheringRole(newGathering, roleRequest.getRole(), roleRequest.getRequiredNumber()));

                }

            });


            registerRequest.getTechStacks().forEach(stack -> {
                gatheringTechStackRepository.save(new GatheringTechStack(
                        newGathering,
                        techStackRepository.findById(stack).get()));
            });

        } catch (DataAccessException e) {
            throw new RuntimeException("모임 등록중 오류 발생", e);
        }
    }
    
    @Override
    public List<TechStackResponse> getAllTechStacks() {
        List<TechStack> techStacks = techStackRepository.findAll();
        return techStacks.stream()
                .map(techStack -> new TechStackResponse(techStack.getId(), techStack.getTechStackName()))
                .toList();
    }
    
    @Transactional
    @Override
    public boolean update(GatheringUpdateRequest request) {
        try {
            Gathering gathering = gatheringRepository.findById(request.getGatheringId())
                    .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다."));
            
            // 모임 호스트만 수정할 수 있도록 검증
            if (!gathering.getHostId().equals(request.getHostId())) {
                return false;
            }
            
            // 현재 인원수가 새로운 최대 인원수보다 많은 경우 검증
            if (gathering.getCurrentMemberCount() > request.getMaxMemberCount()) {
                return false;
            }
            
            // 모임 정보 업데이트
            gathering.update(
                    request.getTeamName(),
                    request.getTitle(),
                    request.getDescription(),
                    request.getMaxMemberCount()
            );
            
            gatheringRepository.save(gathering);
            return true;
        } catch (Exception e) {
            log.error("모임 수정 중 오류 발생", e);
            return false;
        }
    }
    
    @Transactional
    @Override
    public boolean delete(Long gatheringId, Long hostId) {
        try {
            Gathering gathering = gatheringRepository.findById(gatheringId)
                    .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다."));
            
            // 모임 호스트만 삭제할 수 있도록 검증
            if (!gathering.getHostId().equals(hostId)) {
                return false;
            }
            
            // 관련 데이터 삭제
            // 1. 모임 기술 스택 삭제
            gatheringTechStackRepository.deleteByGatheringId(gatheringId);
            
            // 2. 모임 역할 삭제
            gatheringRoleRepository.deleteByGatheringId(gatheringId);
            
            // 3. 모임 멤버 기술 스택 삭제
            List<GatheringMember> members = gatheringMemberRepository.findByGatheringId(gatheringId);
            for (GatheringMember member : members) {
                gatheringMemberTechStackRepository.deleteByGatheringMemberId(member.getId());
            }
            
            // 4. 모임 멤버 삭제
            gatheringMemberRepository.deleteByGatheringId(gatheringId);
            
            // 5. 모임 신청 기술 스택 삭제
            List<GatheringApplication> applications = gatheringApplicationRepository.findByGatheringId(gatheringId);
            for (GatheringApplication application : applications) {
                gatheringApplicationTechStackRepository.deleteByGatheringApplicationId(application.getId());
            }
            
            // 6. 모임 신청 삭제
            gatheringApplicationRepository.deleteByGatheringId(gatheringId);
            
            // 7. 모임 삭제
            gatheringRepository.deleteById(gatheringId);
            
            return true;
        } catch (Exception e) {
            log.error("모임 삭제 중 오류 발생", e);
            return false;
        }
    }
    
    @Override
    public List<RoleResponse> getAllRoles() {
        return Arrays.stream(Role.values())
                .map(RoleResponse::from)
                .collect(Collectors.toList());
    }
}
