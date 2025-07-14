package com.msa.gathering.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class TechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tech_stack_name")
    private String techStackName;


}
