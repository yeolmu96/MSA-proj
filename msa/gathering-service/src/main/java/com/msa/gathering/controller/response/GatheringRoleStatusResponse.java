package com.msa.gathering.controller.response;

import com.msa.gathering.entity.GatheringRole;
import com.msa.gathering.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatheringRoleStatusResponse {
    private Long id;
    private Role role;
    private int requiredNumber;
    private int currentNumber;
    private int remainingSpots;

    public GatheringRoleStatusResponse(Long id, Role role, int requiredNumber, int currentNumber) {
        this.id = id;
        this.role = role;
        this.requiredNumber = requiredNumber;
        this.currentNumber = currentNumber;
        this.remainingSpots = requiredNumber - currentNumber;
    }
    
    public GatheringRoleStatusResponse(GatheringRole gatheringRole) {
        this.id = gatheringRole.getId();
        this.role = gatheringRole.getRole();
        this.requiredNumber = gatheringRole.getRequiredNumber();
        this.currentNumber = gatheringRole.getCurrentNumber();
        this.remainingSpots = gatheringRole.getRequiredNumber() - gatheringRole.getCurrentNumber();
    }
}
