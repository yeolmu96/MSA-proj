package com.msa.gathering.service;

import com.msa.gathering.client.AccountClient;
import com.msa.gathering.controller.request.*;
import com.msa.gathering.controller.response.*;
import com.msa.gathering.entity.ContactInfo;
import com.msa.gathering.entity.*;
import com.msa.gathering.repository.*;
import com.msa.gathering.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
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
    private final ContactInfoRepository contactInfoRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
    private final CommentRepository commentRepository;

    @Override
    public boolean updatePoint(Long accountId, PointReason reason) {
        try {
            UpdatePointRequest request = new UpdatePointRequest(accountId, reason);
            accountClient.updatePoint(request);
            log.info("포인트 업데이트 요청 성공: 사용자 ID={}, 이유={}", accountId, reason);
            return true;
        } catch (Exception e) {
            log.error("포인트 업데이트 요청 중 오류 발생: {}", e.getMessage(), e);
            return false;
        }
    }





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
            // 모임 생성 및 저장
            Gathering newGathering = new Gathering(registerRequest.getAccountId(), registerRequest.getTeamName(),
                    registerRequest.getTitle(), registerRequest.getDescription(), registerRequest.getMaxMemberCount(),
                    registerRequest.getCurrentMemberCount());

            gatheringRepository.save(newGathering);

            // 모임 멤버 생성 및 저장
            GatheringMember newGatheringMember = new GatheringMember(registerRequest.getAccountId(),
                    registerRequest.getRole(), newGathering, true);

            gatheringMemberRepository.save(newGatheringMember);

            // 호스트 기술 스택 저장
            registerRequest.getHostTechStacks().stream().forEach(techStack -> {
                TechStack tech = techStackRepository.findById(techStack)
                        .orElseThrow(() -> new RuntimeException("기술을 찾는 도중 오류 발생"));
                gatheringMemberTechStackRepository.save(new GatheringMemberTechStack(newGatheringMember, tech));
            });

            // 역할 요청 저장
            registerRequest.getRoleRequests().forEach(roleRequest -> {
                if(roleRequest.getRole().equals(registerRequest.getRole())) {
                    gatheringRoleRepository.save(new GatheringRole(newGathering, roleRequest.getRole(), roleRequest.getRequiredNumber(),1));
                }
                else{
                    gatheringRoleRepository.save(new GatheringRole(newGathering, roleRequest.getRole(), roleRequest.getRequiredNumber()));
                }
            });

            // 기술 스택 저장
            registerRequest.getTechStacks().forEach(stack -> {
                gatheringTechStackRepository.save(new GatheringTechStack(
                        newGathering,
                        techStackRepository.findById(stack).get()));
            });
            
            // 계정 서비스에 포인트 업데이트 요청 보내기
            try {
                UpdatePointRequest pointRequest = new UpdatePointRequest(
                        registerRequest.getAccountId(),
                        PointReason.GATHERING_CREATE
                );
                accountClient.updatePoint(pointRequest);
                log.info("모임 생성으로 인한 포인트 업데이트 요청 성공: 사용자 ID={}", registerRequest.getAccountId());
            } catch (Exception e) {
                log.error("포인트 업데이트 요청 중 오류 발생: {}", e.getMessage(), e);
                // 포인트 업데이트 실패해도 모임 생성은 계속 진행
            }

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
            
            // 7. 모임 댓글 삭제
            commentRepository.deleteByGatheringId(gatheringId);
            
            // 8. 모임 삭제
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
    
    @Override
    @Transactional
    public boolean startGathering(Long gatheringId, Long hostId) {
        try {
            Gathering gathering = gatheringRepository.findById(gatheringId)
                    .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다."));
            
            // 모임 호스트만 시작할 수 있도록 검증
            if (!gathering.getHostId().equals(hostId)) {
                return false;
            }
            
            // 모집 상태인지 확인
            if (gathering.getStatus() != GatheringStatus.RECRUITING) {
                return false;
            }
            
            // 모든 인원이 모집되었는지 확인
            if (gathering.getCurrentMemberCount() < gathering.getMaxMemberCount()) {
                return false;
            }
            
            // 모임 상태를 STARTED로 변경
            gathering.startGathering();
            gatheringRepository.save(gathering);
            
            // 신청자들에게 모임 시작 알림 생성
            List<GatheringMember> members = gatheringMemberRepository.findByGatheringId(gatheringId);
            for (GatheringMember member : members) {
                if (!member.getAccountId().equals(hostId)) { // 호스트를 제외한 멤버들에게만 알림 생성
                    notificationService.createNotification(
                            member.getAccountId(),
                            "모임이 시작되었습니다.",
                            "모임 '"+gathering.getTitle()+"'가 시작되었습니다. 이제 연락처 정보를 공유할 수 있습니다.",
                            "/gathering/"+gatheringId
                    );
                }
            }
            
            return true;
        } catch (Exception e) {
            log.error("모임 시작 중 오류 발생", e);
            return false;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MyGatheringResponse> getMyGatherings(Long accountId) {
        // 해당 사용자가 참여한 모임 멤버 목록 조회
        List<GatheringMember> members = gatheringMemberRepository.findByAccountId(accountId);
        
        // 참여한 모임 ID 목록 추출
        List<Long> gatheringIds = members.stream()
                .map(member -> member.getGathering().getId())
                .collect(Collectors.toList());
        
        // 모임 정보 조회
        List<Gathering> gatherings = gatheringRepository.findAllById(gatheringIds);
        
        // 시작된 모임만 필터링 (STARTED 상태인 모임만)
        gatherings = gatherings.stream()
                .filter(gathering -> gathering.getStatus() == GatheringStatus.STARTED)
                .collect(Collectors.toList());
        
        // 모임별 기술 스택 조회
        List<GatheringTechStack> allTechStacks = gatheringTechStackRepository.findByGatheringIdIn(gatheringIds);
        Map<Long, List<GatheringTechStack>> techStacksByGathering = allTechStacks.stream()
                .collect(Collectors.groupingBy(techStack -> techStack.getGathering().getId()));
        
        // 모임별 역할 상태 조회
        List<GatheringRole> allRoles = gatheringRoleRepository.findByGatheringIdIn(gatheringIds);
        Map<Long, List<GatheringRole>> rolesByGathering = allRoles.stream()
                .collect(Collectors.groupingBy(role -> role.getGathering().getId()));
        
        // 모임별 멤버 조회
        List<GatheringMember> allMembers = gatheringMemberRepository.findByGatheringIdIn(gatheringIds);
        Map<Long, List<GatheringMember>> membersByGathering = allMembers.stream()
                .collect(Collectors.groupingBy(member -> member.getGathering().getId()));
        
        // 응답 객체 생성
        return gatherings.stream().map(gathering -> {
            // 기술 스택 응답 생성
            List<TechStackResponse> techStackResponses = techStacksByGathering.getOrDefault(gathering.getId(), Collections.emptyList())
                    .stream()
                    .map(techStack -> {
                        TechStack ts = techStackRepository.findById(techStack.getTechStack().getId())
                                .orElseThrow(() -> new RuntimeException("기술 스택을 찾을 수 없습니다."));
                        return TechStackResponse.builder()
                                .id(ts.getId())
                                .name(ts.getTechStackName())
                                .build();
                    })
                    .collect(Collectors.toList());
            
            // 역할 상태 응답 생성
            List<GatheringRoleStatusResponse> roleStatusResponses = rolesByGathering.getOrDefault(gathering.getId(), Collections.emptyList())
                    .stream()
                    .map(role -> {
                        // 해당 역할의 현재 인원 수 계산
                        long currentCount = membersByGathering.getOrDefault(gathering.getId(), Collections.emptyList())
                                .stream()
                                .filter(member -> member.getRole() == role.getRole())
                                .count();
                        
                        return GatheringRoleStatusResponse.builder()
                                .role(role.getRole())
                                .requiredNumber(role.getRequiredNumber())
                                .currentNumber((int) currentCount)
                                .build();
                    })
                    .collect(Collectors.toList());
            
            return MyGatheringResponse.from(gathering, techStackResponses, roleStatusResponses);
        }).collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public boolean saveContactInfo(ContactInfoRequest request) {
        try {
            // 모임 조회
            Gathering gathering = gatheringRepository.findById(request.getGatheringId())
                    .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다."));
            
            // 모임이 시작되었는지 확인
            if (gathering.getStatus() != GatheringStatus.STARTED) {
                return false;
            }
            
            // 사용자가 모임의 멤버인지 확인
            List<GatheringMember> members = gatheringMemberRepository.findByGatheringId(gathering.getId());
            boolean isMember = members.stream()
                    .anyMatch(member -> member.getAccountId().equals(request.getAccountId()));
            
            if (!isMember) {
                return false;
            }
            
            // 기존 연락처 정보 확인
            ContactInfo contactInfo = contactInfoRepository.findByGatheringIdAndAccountId(
                    request.getGatheringId(), request.getAccountId())
                    .orElse(null);
            
            if (contactInfo == null) {
                // 새로운 연락처 정보 생성
                contactInfo = ContactInfo.builder()
                        .gathering(gathering)
                        .accountId(request.getAccountId())
                        .phoneNumber(request.getPhoneNumber())
                        .email(request.getEmail())
                        .openChatLink(request.getOpenChatLink())
                        .additionalInfo(request.getAdditionalInfo())
                        .build();
            } else {
                // 기존 연락처 정보 업데이트
                contactInfo.update(
                        request.getPhoneNumber(),
                        request.getEmail(),
                        request.getOpenChatLink(),
                        request.getAdditionalInfo()
                );
            }
            
            contactInfoRepository.save(contactInfo);
            return true;
        } catch (Exception e) {
            log.error("연락처 정보 저장 중 오류 발생", e);
            return false;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ContactInfoResponse> getContactInfos(Long gatheringId, Long accountId) {
        try {
            // 모임 조회
            Gathering gathering = gatheringRepository.findById(gatheringId)
                    .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다."));
            
            // 모임이 시작되었는지 확인
            if (gathering.getStatus() != GatheringStatus.STARTED) {
                return Collections.emptyList();
            }
            
            // 사용자가 모임의 멤버인지 확인
            List<GatheringMember> members = gatheringMemberRepository.findByGatheringId(gathering.getId());
            boolean isMember = members.stream()
                    .anyMatch(member -> member.getAccountId().equals(accountId));
            
            if (!isMember) {
                return Collections.emptyList();
            }
            
            // 모임의 모든 연락처 정보 조회
            List<ContactInfo> contactInfos = contactInfoRepository.findByGatheringId(gatheringId);
            
            // 응답 객체 변환
            return contactInfos.stream()
                    .map(ContactInfoResponse::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("연락처 정보 조회 중 오류 발생", e);
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public GatheringDetailResponse getGatheringDetail(Long gatheringId) {
        try {
            // 모임 조회
            Gathering gathering = gatheringRepository.findById(gatheringId)
                    .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다."));
            
            // 모임 멤버 조회
            List<GatheringMember> members = gatheringMemberRepository.findByGatheringId(gatheringId);
            
            // 모임 기술 스택 조회
            List<GatheringTechStack> techStacks = gatheringTechStackRepository.findByGatheringId(gatheringId);
            
            // 모임 역할 조회
            List<GatheringRole> roles = gatheringRoleRepository.findByGatheringId(gatheringId);
            
            // 멤버 정보 조회 요청 생성
            List<GatheringAccountRequest> accountRequests = members.stream()
                    .map(member -> new GatheringAccountRequest(
                            member.getAccountId(),
                            gatheringId,
                            member.getRole(),
                            member.isHost()))
                    .toList();
            
            // 계정 정보 조회
            List<AccountInfoResponse> accountInfos = accountClient.getList(accountRequests);
            
            // 기술 스택 응답 변환
            List<TechStackResponse> techStackResponses = techStacks.stream()
                    .map(ts -> new TechStackResponse(ts.getTechStack()))
                    .toList();
            
            // 역할 상태 응답 변환
            List<GatheringRoleStatusResponse> roleStatusResponses = roles.stream()
                    .map(role -> {
                        // 각 역할별 현재 인원수 계산
                        long currentCount = members.stream()
                                .filter(member -> member.getRole() == role.getRole())
                                .count();
                        
                        return GatheringRoleStatusResponse.builder()
                                .role(role.getRole())
                                .requiredNumber(role.getRequiredNumber())
                                .currentNumber((int) currentCount)
                                .build();
                    })
                    .toList();
            
            // 응답 객체 생성 및 반환
            return GatheringDetailResponse.from(gathering, accountInfos, techStackResponses, roleStatusResponses);
        } catch (Exception e) {
            log.error("모임 상세 정보 조회 중 오류 발생", e);
            throw new RuntimeException("모임 상세 정보를 조회할 수 없습니다.", e);
        }
    }
}
