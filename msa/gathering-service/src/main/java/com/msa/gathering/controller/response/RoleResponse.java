package com.msa.gathering.controller.response;

import com.msa.gathering.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    private String name;
    
    public static RoleResponse from(Role role) {
        return new RoleResponse(role.name());
    }
}
