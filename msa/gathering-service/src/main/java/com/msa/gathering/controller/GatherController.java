package com.msa.gathering.controller;


import com.msa.gathering.controller.request.GatheringListRequest;
import com.msa.gathering.service.GatheringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gathering")
@RequiredArgsConstructor
public class GatherController {

    private final GatheringService gatheringService;


    @GetMapping("/list")
    public ResponseEntity<List<GatheringListRequest>> list() {

        List<GatheringListRequest> gatheringList = gatheringService.getList();

        return ResponseEntity.ok(gatheringList);

    }


}
