package com.msa.account.client;

import com.msa.account.controller.request.UpdatePointRequest;
import com.msa.account.controller.response.UpdatePointResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service")
public interface AccountPointClient {

    @PostMapping("/account/point/update")
    UpdatePointResponse updatePoint(@RequestBody UpdatePointRequest request);
}
