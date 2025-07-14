package com.msa.gathering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GatheringServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatheringServiceApplication.class, args);
    }

}
