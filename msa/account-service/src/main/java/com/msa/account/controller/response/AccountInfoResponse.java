package com.msa.account.controller.response;

import com.msa.account.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AccountInfoResponse {

    private String userId;
    private Long gatheringId;
    private String nickname;
    private Role role;
    private boolean isHost;
}
