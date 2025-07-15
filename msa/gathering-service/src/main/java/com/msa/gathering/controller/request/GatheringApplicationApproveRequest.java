package com.msa.gathering.controller.request;


import com.msa.gathering.entity.TechStack;
import lombok.Getter;

import java.util.List;

@Getter
public class GatheringApplicationApproveRequest {

    private Long applicationId;

    private Boolean approved;



}
