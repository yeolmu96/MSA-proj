package com.msa.gathering.controller;


import com.msa.gathering.controller.request.GatheringApplicationApproveRequest;
import com.msa.gathering.controller.request.GatheringApplicationRequest;
import com.msa.gathering.controller.request.GatheringListRequest;
import com.msa.gathering.controller.request.GatheringRegisterRequest;
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

    @GetMapping("/test")
    public String test(){
        return "test success";
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
