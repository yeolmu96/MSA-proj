package com.msa.gathering.service;

import com.msa.gathering.controller.request.GatheringApplicationRequest;
import com.msa.gathering.controller.request.GatheringListRequest;
import com.msa.gathering.controller.request.GatheringRegisterRequest;

import java.util.List;

public interface GatheringService {
    List<GatheringListRequest> getList();
    void register(GatheringRegisterRequest  gatheringRegisterRequest);


}
