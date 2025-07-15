package com.msa.gathering.controller.request;


import com.msa.gathering.entity.Role;
import lombok.Getter;

import java.util.List;

@Getter
public class GatheringRegisterRequest {

    private Long accountId;

    private Role role;

    private String teamName;

    private String title;

    private String description;

    private int maxMemberCount;

    private int currentMemberCount;

    private List<Long> techStacks;

    private List<Long> hostTechStacks;

    private List<RoleRequest> roleRequests;





}
