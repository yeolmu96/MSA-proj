package com.msa.gathering.controller.request;

import com.msa.gathering.entity.Role;
import lombok.Getter;

@Getter
public class RoleRequest {

    private Role role;

    private int requiredNumber;

    public void setRequiredNumber(int requiredNumber) {
        this.requiredNumber = requiredNumber;
    }
}
