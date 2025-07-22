package com.msa.gathering.controller;


import com.msa.gathering.client.AccountClient;
import com.msa.gathering.controller.request.*;
import com.msa.gathering.controller.response.*;
import com.msa.gathering.controller.response.GatheringDetailResponse;
import com.msa.gathering.entity.Gathering;
import com.msa.gathering.entity.GatheringMember;
import com.msa.gathering.repository.GatheringMemberRepository;
import com.msa.gathering.repository.GatheringRepository;
import com.msa.gathering.service.GatheringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gathering")
@RequiredArgsConstructor
public class GatherController {

    private final GatheringService gatheringService;
    private final AccountClient accountClient;
    private final GatheringRepository gatheringRepository;
    private final GatheringMemberRepository gatheringMemberRepository;

    @GetMapping("/test")
    public String test(){
        return "test success";
    }
    
    @PostMapping("/update-point")
    public ResponseEntity<?> updateAccountPoint(@RequestBody UpdatePointRequest request) {
        boolean result = gatheringService.updatePoint(request.getAccountId(), request.getReason());
        if (result) {
            return ResponseEntity.ok("포인트 업데이트가 성공적으로 요청되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("포인트 업데이트 요청에 실패했습니다.");
        }
    }

    @GetMapping("/test2")
    public ResponseEntity<List<AccountInfoResponse>> test2(){

        List<Gathering> allGatherings = gatheringRepository.findAll();

        List<Long> gatheringIds = allGatherings.stream()
                .map(Gathering::getId)
                .toList();

        List<GatheringMember> allMembers = gatheringMemberRepository.findByGatheringIdIn(gatheringIds);

        List<GatheringAccountRequest> accountRequests = allMembers.stream()
                .map(m -> new GatheringAccountRequest(
                        m.getAccountId(),
                        m.getGathering().getId(),
                        m.getRole(),
                        m.isHost()))
                .toList();

        List<AccountInfoResponse> accountInfos = accountClient.getList(accountRequests);
        return ResponseEntity.ok(accountInfos);
    }

    @GetMapping("/test3")
    public ResponseEntity<List<GatheringAccountRequest>> test3(){

        List<Gathering> allGatherings = gatheringRepository.findAll();

        List<Long> gatheringIds = allGatherings.stream()
                .map(Gathering::getId)
                .toList();

        List<GatheringMember> allMembers = gatheringMemberRepository.findByGatheringIdIn(gatheringIds);

        List<GatheringAccountRequest> accountRequests = allMembers.stream()
                .map(m -> new GatheringAccountRequest(
                        m.getAccountId(),
                        m.getGathering().getId(),
                        m.getRole(),
                        m.isHost()))
                .toList();

        return ResponseEntity.ok(accountRequests);
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody GatheringRegisterRequest gatheringRegisterRequest) {
        gatheringService.register(gatheringRegisterRequest);
        return ResponseEntity.ok("등록 성공");
    }


    @GetMapping("/list")
    public ResponseEntity<List<GatheringListRequest>> list() {
        List<GatheringListRequest> gatheringList = gatheringService.getList();
        return ResponseEntity.ok(gatheringList);
    }
    
    @GetMapping("/tech-stacks")
    public ResponseEntity<List<TechStackResponse>> getTechStacks() {
        List<TechStackResponse> techStacks = gatheringService.getAllTechStacks();
        return ResponseEntity.ok(techStacks);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateGathering(@RequestBody GatheringUpdateRequest request) {
        boolean result = gatheringService.update(request);
        if (result) {
            return ResponseEntity.ok("모임 정보가 성공적으로 수정되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("모임 정보 수정에 실패했습니다. 호스트 권한을 확인하거나 최대 인원수를 확인해주세요.");
        }
    }
    
    @DeleteMapping("/delete/{gatheringId}/{hostId}")
    public ResponseEntity<?> deleteGathering(@PathVariable Long gatheringId, @PathVariable Long hostId) {
        boolean result = gatheringService.delete(gatheringId, hostId);
        if (result) {
            return ResponseEntity.ok("모임이 성공적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("모임 삭제에 실패했습니다. 호스트 권한을 확인해주세요.");
        }
    }
    
    @GetMapping("/roles")
    public ResponseEntity<List<RoleResponse>> getRoles() {
        List<RoleResponse> roles = gatheringService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
    
    @GetMapping("/my-gatherings/{accountId}")
    public ResponseEntity<List<MyGatheringResponse>> getMyGatherings(@PathVariable Long accountId) {
        List<MyGatheringResponse> gatherings = gatheringService.getMyGatherings(accountId);
        return ResponseEntity.ok(gatherings);
    }
    
    @PostMapping("/start/{gatheringId}/{hostId}")
    public ResponseEntity<?> startGathering(@PathVariable Long gatheringId, @PathVariable Long hostId) {
        boolean result = gatheringService.startGathering(gatheringId, hostId);
        if (result) {
            return ResponseEntity.ok("모임이 성공적으로 시작되었습니다. 이제 연락처 정보를 공유할 수 있습니다.");
        } else {
            return ResponseEntity.badRequest().body("모임 시작에 실패했습니다. 호스트 권한을 확인하거나 모든 인원이 모집되었는지 확인해주세요.");
        }
    }
    
    @PostMapping("/contact-info")
    public ResponseEntity<?> saveContactInfo(@RequestBody ContactInfoRequest request) {
        boolean result = gatheringService.saveContactInfo(request);
        if (result) {
            return ResponseEntity.ok("연락처 정보가 성공적으로 저장되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("연락처 정보 저장에 실패했습니다. 모임이 시작되었는지 혹은 모임 멤버인지 확인해주세요.");
        }
    }
    
    @GetMapping("/contact-info/{gatheringId}/{accountId}")
    public ResponseEntity<List<ContactInfoResponse>> getContactInfos(@PathVariable Long gatheringId, @PathVariable Long accountId) {
        List<ContactInfoResponse> contactInfos = gatheringService.getContactInfos(gatheringId, accountId);
        return ResponseEntity.ok(contactInfos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getGatheringDetail(@PathVariable("id") Long gatheringId) {
        try {
            GatheringDetailResponse detailResponse = gatheringService.getGatheringDetail(gatheringId);
            return ResponseEntity.ok(detailResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
