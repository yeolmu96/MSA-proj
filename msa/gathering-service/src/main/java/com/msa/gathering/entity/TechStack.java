package com.msa.gathering.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tech_stack", nullable = false, unique = true)
    private TechStacks techStack;

    @Column(name = "tech_stack_name", nullable = false)
    private String techStackName;

    public TechStack(TechStacks techStack) {
        this.techStack = techStack;
        this.techStackName = techStack.getDisplayName(); // enum에서 이름 가져오기
    }

}
