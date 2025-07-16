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
public class GatheringDetailResponse {
    private Long id;
    private String teamName;
    private String title;
    private String description;
    private int maxMemberCount;
    private int currentMemberCount;
    private LocalDateTime createdAt;
    private GatheringStatus status;
    private List<AccountInfoResponse> members;
    private List<TechStackResponse> techStacks;
    private List<GatheringRoleStatusResponse> roles;
    private Long hostId;
    
    public static GatheringDetailResponse from(
            Gathering gathering,
            List<AccountInfoResponse> members,
            List<TechStackResponse> techStacks,
            List<GatheringRoleStatusResponse> roles
    ) {
        return GatheringDetailResponse.builder()
                .id(gathering.getId())
                .teamName(gathering.getTeamName())
                .title(gathering.getTitle())
                .description(gathering.getDescription())
                .maxMemberCount(gathering.getMaxMemberCount())
                .currentMemberCount(gathering.getCurrentMemberCount())
                .createdAt(gathering.getCreatedAt())
                .status(gathering.getStatus())
                .members(members)
                .techStacks(techStacks)
                .roles(roles)
                .hostId(gathering.getHostId())
                .build();
    }
}
