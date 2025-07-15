package com.msa.gathering.controller.request;

import com.msa.gathering.entity.Role;
import lombok.Getter;

@Getter
public class GatheringAccountRequest {

    private Long accountId;

    private Long GatheringId;

    private Role role;

    private Boolean isHost;

    public GatheringAccountRequest(Long accountId, Long gatheringId, Role role, boolean isHost) {
        this.accountId = accountId;
        GatheringId = gatheringId;
        this.role = role;
        this.isHost = isHost;
    }
}
