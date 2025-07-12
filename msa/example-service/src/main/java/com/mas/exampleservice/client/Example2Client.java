package com.mas.exampleservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient( name = "example2-service")
public interface Example2Client {

    @GetMapping("/example2/twoTest")
    String twoTest();

}
