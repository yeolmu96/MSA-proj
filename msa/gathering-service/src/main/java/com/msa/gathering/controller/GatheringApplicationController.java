package com.msa.gathering.controller;


import com.msa.gathering.controller.request.GatheringApplicationApproveRequest;
import com.msa.gathering.controller.request.GatheringApplicationRequest;
import com.msa.gathering.repository.GatheringApplicationRepository;
import com.msa.gathering.service.GatheringApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gathering")
public class GatheringApplicationController {
    private final GatheringApplicationService gatheringApplicationService;

    @PostMapping("/application")
    public ResponseEntity<String> application(@RequestBody GatheringApplicationRequest gatheringApplicationRequest) {

        gatheringApplicationService.application(gatheringApplicationRequest);
        return ResponseEntity.ok("신청 완료");

    }

    @PostMapping("/approve")
    public ResponseEntity<String> approve(@RequestBody GatheringApplicationApproveRequest gatheringApplicationApproveRequest) {
        gatheringApplicationService.approve(gatheringApplicationApproveRequest);

        return ResponseEntity.ok("확인 완료");
    }


}
