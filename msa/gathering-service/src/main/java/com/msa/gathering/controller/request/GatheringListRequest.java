package com.msa.gathering.controller.request;

import com.msa.gathering.controller.response.AccountInfoResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class GatheringListRequest {

    private Long gatheringId;

    private List<AccountInfoResponse> gatheringMembers;

    private String teamName;

    private String title;

    private int maxMemberCount;

    private int currentMemberCount;

    public GatheringListRequest(Long gatheringId, List<AccountInfoResponse> gatheringMembers,
                                String teamName, String title, int maxMemberCount, int currentMemberCount) {
        this.gatheringId = gatheringId;
        this.gatheringMembers = gatheringMembers;
        this.teamName = teamName;
        this.title = title;
        this.maxMemberCount = maxMemberCount;
        this.currentMemberCount = currentMemberCount;
    }
}
