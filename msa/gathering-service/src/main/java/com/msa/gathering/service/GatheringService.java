package com.msa.gathering.service;

import com.msa.gathering.controller.request.GatheringListRequest;
import com.msa.gathering.entity.GatheringMember;

import java.util.List;

public interface GatheringService {
    List<GatheringListRequest> getList();
}
