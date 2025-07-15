package com.msa.gathering.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class GatheringTechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Gathering gathering;

    @ManyToOne(fetch = FetchType.LAZY)
    private TechStack techStack;

    public GatheringTechStack(Gathering gathering, TechStack techStack) {
        this.gathering = gathering;
        this.techStack = techStack;
    }
}
