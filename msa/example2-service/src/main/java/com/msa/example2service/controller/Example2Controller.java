package com.msa.example2service.controller;


import com.msa.example2service.service.service.Example2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/example2")
public class Example2Controller {

    private final Example2Service example2Service;



    @GetMapping("/twoTest")
    public String towTest(){
        return example2Service.getTowTestCode().getCode();
    }
}
