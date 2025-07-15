package com.msa.reviewservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingResponse {
    private Long id;
    private String name;
    private NcsType ncsType;
    private String ncsTypeDescription;
    private int period;
    private Long institutionId;
    private String institutionName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;

}
