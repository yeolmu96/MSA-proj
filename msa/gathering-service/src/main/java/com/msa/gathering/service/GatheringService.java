package com.msa.gathering.service;

import com.msa.gathering.controller.request.*;
import com.msa.gathering.controller.response.ContactInfoResponse;
import com.msa.gathering.controller.response.GatheringDetailResponse;
import com.msa.gathering.controller.response.MyGatheringResponse;
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
    
    // 포인트 업데이트 요청
    boolean updatePoint(Long accountId, PointReason reason);
    
    // 내 모임 목록 조회
    List<MyGatheringResponse> getMyGatherings(Long accountId);
    
    // 모임 시작 (모든 인원이 모집되었을 때)
    boolean startGathering(Long gatheringId, Long hostId);
    
    // 연락처 정보 등록/수정
    boolean saveContactInfo(ContactInfoRequest request);
    
    // 모임 내 연락처 정보 목록 조회
    List<ContactInfoResponse> getContactInfos(Long gatheringId, Long accountId);
    
    // 모임 상세 정보 조회
    GatheringDetailResponse getGatheringDetail(Long gatheringId);
}
