package com.msa.gathering.client;

import com.msa.gathering.controller.request.GatheringAccountRequest;
import com.msa.gathering.controller.request.UpdatePointRequest;
import com.msa.gathering.controller.response.AccountInfoResponse;
import com.msa.gathering.controller.response.UpdatePointResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "account-service")
public interface AccountClient {

    @PostMapping("/account/gathering-info")
    List<AccountInfoResponse> getList(@RequestBody List<GatheringAccountRequest> ids);
    
    @PostMapping("/account/update-point")
    ResponseEntity<UpdatePointResponse> updatePoint(@RequestBody UpdatePointRequest request);
}
