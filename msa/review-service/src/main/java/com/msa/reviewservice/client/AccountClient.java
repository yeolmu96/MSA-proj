package com.msa.reviewservice.client;

import com.msa.reviewservice.controller.response.IdAccountResponse;
import com.msa.reviewservice.controller.response.ReviewAccountInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/account/find-id")
    IdAccountResponse getAccountId(@RequestHeader("Authorization") String token);

    @GetMapping("/account/review-info")
    ReviewAccountInfoResponse getAccountInfo(@RequestParam("accountId") Long accountId);
}
