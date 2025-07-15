package com.msa.gathering.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class GatheringApplicationTechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private GatheringApplication gatheringApplication;

    @ManyToOne(fetch = FetchType.LAZY)
    private TechStack techStack;

    public GatheringApplicationTechStack(GatheringApplication gatheringApplication, TechStack techStack) {
        this.gatheringApplication = gatheringApplication;
        this.techStack = techStack;
    }
}
