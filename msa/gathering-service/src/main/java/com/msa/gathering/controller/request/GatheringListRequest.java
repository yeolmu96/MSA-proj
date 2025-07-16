package com.msa.gathering.controller.request;

import com.msa.gathering.controller.response.AccountInfoResponse;
import com.msa.gathering.controller.response.GatheringRoleStatusResponse;
import com.msa.gathering.controller.response.TechStackResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GatheringListRequest {

    private Long gatheringId;

    private List<AccountInfoResponse> gatheringMembers;

    private String teamName;

    private String title;

    private int maxMemberCount;

    private int currentMemberCount;
    
    private LocalDateTime createdAt;
    
    private List<TechStackResponse> techStacks;
    
    private List<GatheringRoleStatusResponse> roleStatus;

    public GatheringListRequest(Long gatheringId, List<AccountInfoResponse> gatheringMembers,
                                String teamName, String title, int maxMemberCount, int currentMemberCount,
                                LocalDateTime createdAt, List<TechStackResponse> techStacks,
                                List<GatheringRoleStatusResponse> roleStatus) {
        this.gatheringId = gatheringId;
        this.gatheringMembers = gatheringMembers;
        this.teamName = teamName;
        this.title = title;
        this.maxMemberCount = maxMemberCount;
        this.currentMemberCount = currentMemberCount;
        this.createdAt = createdAt;
        this.techStacks = techStacks;
        this.roleStatus = roleStatus;
    }
}
