package com.msa.account.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "institution-service", url = "https://institution-service")
public interface InstitutionClient {

    @GetMapping("/api/institutions/{id}/exists")
    boolean checkInstitutionExists(@PathVariable("id") Long id);
}
