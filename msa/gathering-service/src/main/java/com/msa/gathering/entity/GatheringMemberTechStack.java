package com.msa.gathering.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class GatheringMemberTechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private GatheringMember gatheringMember;

    @OneToOne(fetch = FetchType.LAZY)
    private TechStack techStack;


}
