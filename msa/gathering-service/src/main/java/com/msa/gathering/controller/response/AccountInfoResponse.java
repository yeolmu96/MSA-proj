package com.msa.gathering.controller.response;


import com.msa.gathering.entity.Role;
import lombok.Getter;

@Getter
public class AccountInfoResponse {

    private Long accountId;

    private Long GatheringId;

    private String nickName;

    private Role role;

    private boolean isHost;


    public AccountInfoResponse(Long accountId, Long gatheringId, String nickName, Role role, boolean isHost) {
        this.accountId = accountId;
        GatheringId = gatheringId;
        this.nickName = nickName;
        this.role = role;
        this.isHost = isHost;
    }
}
