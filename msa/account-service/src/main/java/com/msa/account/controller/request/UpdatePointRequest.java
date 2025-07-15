package com.msa.account.controller.request;

import com.msa.account.entity.PointReason;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePointRequest {
    private Long accountId;
    private Integer point; //증감할 포인트
    private PointReason reason; //(예: REVIEW_WRITE, REVIEW_READ, GATHERING_CREATE 등)
}
