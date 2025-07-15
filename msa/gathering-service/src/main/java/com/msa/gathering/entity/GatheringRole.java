package com.msa.gathering.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class GatheringRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Gathering gathering;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private Role role;

    @Column(name = "required_number")
    private int requiredNumber;

    @Column(name = "current_number")
    private int currentNumber = 0;


    public GatheringRole(Gathering gathering, Role role, int requiredNumber) {
        this.gathering = gathering;
        this.role = role;
        this.requiredNumber = requiredNumber;
    }

    public GatheringRole(Gathering gathering, Role role, int requiredNumber, int currentNumber) {
        this.gathering = gathering;
        this.role = role;
        this.requiredNumber = requiredNumber;
        this.currentNumber = currentNumber;
    }
}
