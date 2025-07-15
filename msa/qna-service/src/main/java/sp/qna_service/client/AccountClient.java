package sp.qna_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import sp.qna_service.controller.response.MyAccountInfoResponse;

@FeignClient(name = "account-service")
public interface AccountClient {
    @GetMapping(value = "/account/me")
    MyAccountInfoResponse getMe(@RequestHeader("Authorization") String token);
}
