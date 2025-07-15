package com.msa.gathering.service;

import com.msa.gathering.controller.request.GatheringApplicationRequest;
import com.msa.gathering.controller.request.GatheringListRequest;
import com.msa.gathering.controller.request.GatheringRegisterRequest;
import com.msa.gathering.controller.request.GatheringUpdateRequest;
import com.msa.gathering.controller.response.RoleResponse;
import com.msa.gathering.controller.response.TechStackResponse;

import java.util.List;

public interface GatheringService {
    List<GatheringListRequest> getList();
    void register(GatheringRegisterRequest  gatheringRegisterRequest);
    List<TechStackResponse> getAllTechStacks();
    boolean update(GatheringUpdateRequest gatheringUpdateRequest);
    boolean delete(Long gatheringId, Long hostId);
    List<RoleResponse> getAllRoles();
}
