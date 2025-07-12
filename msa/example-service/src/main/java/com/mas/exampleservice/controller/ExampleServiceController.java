package com.mas.exampleservice.controller;

import com.mas.exampleservice.client.Example2Client;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
@RequiredArgsConstructor
public class ExampleServiceController {

    private final Example2Client example2Client;


    @GetMapping("/test")
    public String hello() {
        return "Hello example service";
    }

    @GetMapping("/test/feign")
    public String testFeign() {
        String test = example2Client.twoTest();
        return test;
    }
}
