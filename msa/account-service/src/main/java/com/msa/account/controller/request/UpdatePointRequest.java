package com.msa.account.controller.request;

import com.msa.account.entity.PointReason;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePointRequest {
    private Long accountId;
    private PointReason reason; //(예: REVIEW_WRITE, REVIEW_READ, GATHERING_CREATE 등)
}
