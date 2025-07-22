package com.msa.account.controller.request;

import com.msa.account.entity.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GatheringAccountRequest {

    private Long accountId;
    private Long gatheringId;
    private Role role;
    private Boolean isHost;
}
