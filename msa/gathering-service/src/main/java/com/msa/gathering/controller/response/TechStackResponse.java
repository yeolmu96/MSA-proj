package com.msa.gathering.controller.response;

import com.msa.gathering.entity.TechStack;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
public class TechStackResponse {
    private Long id;
    private String name;

    public TechStackResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public TechStackResponse(TechStack techStack) {
        this.id = techStack.getId();
        this.name = techStack.getTechStackName();
    }
}
