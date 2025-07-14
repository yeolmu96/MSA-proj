package com.msa.gathering.service;

import com.msa.gathering.controller.request.GatheringApplicationApproveRequest;
import com.msa.gathering.controller.request.GatheringApplicationRequest;

public interface GatheringApplicationService {

    void application(GatheringApplicationRequest gatheringApplicationRequest);

    void approve(GatheringApplicationApproveRequest gatheringApplicationApproveRequest);
}
