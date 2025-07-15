package com.msa.gathering.controller.response;


import com.msa.gathering.entity.Role;
import lombok.Getter;

@Getter
public class AccountInfoResponse {

    private Long accountId;

    private Long GatheringId;

    private String nickname;

    private Role role;

    private Boolean isHost;


    public AccountInfoResponse(Long accountId, Long gatheringId, String nickName, Role role, boolean isHost) {
        this.accountId = accountId;
        GatheringId = gatheringId;
        this.nickname = nickname;
        this.role = role;
        this.isHost = isHost;
    }
}
