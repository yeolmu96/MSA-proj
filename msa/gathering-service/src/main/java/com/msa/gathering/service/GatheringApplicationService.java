package com.msa.gathering.service;

import com.msa.gathering.controller.request.GatheringApplicationApproveRequest;
import com.msa.gathering.controller.request.GatheringApplicationRequest;

public interface GatheringApplicationService {

    boolean application(GatheringApplicationRequest gatheringApplicationRequest);

    boolean applicationNumberCheck(GatheringApplicationRequest gatheringApplicationRequest);

    void approve(GatheringApplicationApproveRequest gatheringApplicationApproveRequest);
}
