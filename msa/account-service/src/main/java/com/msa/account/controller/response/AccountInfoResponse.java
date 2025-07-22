package com.msa.account.controller.response;

import com.msa.account.entity.Role;
import lombok.*;
@Getter @Setter
@NoArgsConstructor
@ToString
public class AccountInfoResponse {

    private Long accountId;
    private Long gatheringId;
    private String nickname;
    private Role role;
    private Boolean isHost;

    public AccountInfoResponse(Long accountId, Long gatheringId, String nickname, Role role, Boolean isHost) {
        this.accountId = accountId;
        this.gatheringId = gatheringId;
        this.nickname = nickname;
        this.role = role;
        this.isHost = isHost;
    }
}
