package com.msa.gathering.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class GatheringTechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Gathering gathering;

    @OneToOne(fetch = FetchType.LAZY)
    private TechStack techStack;




}
