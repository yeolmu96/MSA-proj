package com.msa.account.controller.request;

import com.msa.account.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GatheringAccountRequest {

    private Long accountId;
    private Long gatheringId;
    private Role role;
    private boolean isHost;
}
