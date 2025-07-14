package com.msa.gathering.config;

import com.msa.gathering.entity.TechStack;
import com.msa.gathering.entity.TechStacks;
import com.msa.gathering.repository.TechStackRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TechStackInitializer implements ApplicationRunner {

    private final TechStackRepository techStackRepository;

    public TechStackInitializer(TechStackRepository techStackRepository) {
        this.techStackRepository = techStackRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        for (TechStacks enumValue : TechStacks.values()) {
            if (!techStackRepository.existsByTechStack(enumValue)) {
                TechStack techStack = new TechStack(enumValue); // displayName 포함됨
                techStackRepository.save(techStack);
            }
        }
    }
}

