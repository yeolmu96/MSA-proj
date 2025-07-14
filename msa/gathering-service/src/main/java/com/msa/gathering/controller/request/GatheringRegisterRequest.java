package com.msa.gathering.controller.request;


import com.msa.gathering.entity.Role;
import lombok.Getter;

@Getter
public class GatheringRegisterRequest {

    private Long accountId;

    private Role role;

    private String teamName;

    private String title;

    private String description;

    private int maxMemberCount;

    private int currentMemberCount;






}
