package com.msa.gathering.controller.request;


import com.msa.gathering.entity.Role;
import com.msa.gathering.entity.TechStack;
import lombok.Getter;

import java.util.List;

@Getter
public class GatheringApplicationRequest {

    private Long accountId;

    private Long gatheringId;

    private Role role;

    private String description;

    private List<Long> techStacks;


}
