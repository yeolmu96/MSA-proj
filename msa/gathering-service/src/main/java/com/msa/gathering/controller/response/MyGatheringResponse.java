package com.msa.gathering.controller.response;

import com.msa.gathering.entity.Gathering;
import com.msa.gathering.entity.GatheringStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyGatheringResponse {
    private Long id;
    private Long hostId;
    private String teamName;
    private String title;
    private String description;
    private int maxMemberCount;
    private int currentMemberCount;
    private GatheringStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TechStackResponse> techStacks;
    private List<GatheringRoleStatusResponse> roleStatuses;
    
    public static MyGatheringResponse from(Gathering gathering, 
                                          List<TechStackResponse> techStacks, 
                                          List<GatheringRoleStatusResponse> roleStatuses) {
        return MyGatheringResponse.builder()
                .id(gathering.getId())
                .hostId(gathering.getHostId())
                .teamName(gathering.getTeamName())
                .title(gathering.getTitle())
                .description(gathering.getDescription())
                .maxMemberCount(gathering.getMaxMemberCount())
                .currentMemberCount(gathering.getCurrentMemberCount())
                .status(gathering.getStatus())
                .createdAt(gathering.getCreatedAt())
                .updatedAt(gathering.getUpdatedAt())
                .techStacks(techStacks)
                .roleStatuses(roleStatuses)
                .build();
    }
}
