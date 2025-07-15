package com.msa.gathering.controller;


import com.msa.gathering.client.AccountClient;
import com.msa.gathering.controller.request.*;
import com.msa.gathering.controller.response.AccountInfoResponse;
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
//        List<GatheringListRequest> gatheringList = gatheringService.getList();
        return ResponseEntity.ok("등록 성공");
    }


    @GetMapping("/list")
    public ResponseEntity<List<GatheringListRequest>> list() {
        List<GatheringListRequest> gatheringList = gatheringService.getList();
        return ResponseEntity.ok(gatheringList);
    }







}
