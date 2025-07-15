package com.msa.gathering.controller;


import com.msa.gathering.controller.request.GatheringApplicationApproveRequest;
import com.msa.gathering.controller.request.GatheringApplicationRequest;
import com.msa.gathering.repository.GatheringApplicationRepository;
import com.msa.gathering.service.GatheringApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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


        boolean applicationNumberCheck = gatheringApplicationService.applicationNumberCheck(gatheringApplicationRequest);
        if (!applicationNumberCheck) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("정원 초과");
        }

        boolean result = gatheringApplicationService.application(gatheringApplicationRequest);
        if (result) {
            return ResponseEntity.ok("Application has been created");
        }
        else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("이미 신청 했습니다.");
        }

    }

    @PostMapping("/approve")
    public ResponseEntity<String> approve(@RequestBody GatheringApplicationApproveRequest gatheringApplicationApproveRequest) {
        gatheringApplicationService.approve(gatheringApplicationApproveRequest);

        return ResponseEntity.ok("확인 완료");
    }


}
