package com.msa.reviewservice.client;

import com.msa.reviewservice.controller.response.ApiResponse;
import com.msa.reviewservice.controller.response.TrainingResponse;
import jakarta.ws.rs.Path;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "information-service")
public interface InfoClient {

    @GetMapping("/training/{id}")
    ApiResponse<TrainingResponse> getTrainingById(@PathVariable Long id);
}
