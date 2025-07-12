package com.msa.example2service.service.service;


import com.msa.example2service.entity.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Example2ServiceImpl implements Example2Service {


    @Override
    public Test getTowTestCode() {
        return new Test("TestCode - 0239429");
    }

}
