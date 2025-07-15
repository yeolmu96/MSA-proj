package com.msa.gathering.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class GatheringMemberTechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private GatheringMember gatheringMember;

    @ManyToOne(fetch = FetchType.LAZY)
    private TechStack techStack;

    public GatheringMemberTechStack(GatheringMember gatheringMember, TechStack techStack) {
        this.gatheringMember = gatheringMember;
        this.techStack = techStack;
    }
}
