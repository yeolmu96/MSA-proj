package com.msa.gathering.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePointResponse {
    private Long accountId;
    private Long updatePoint;
    private String message;
}
