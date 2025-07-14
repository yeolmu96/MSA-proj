package com.msa.gathering.entity;

import com.msa.gathering.controller.request.GatheringApplicationApproveRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class GatheringMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gathering_id", nullable = false)
    private Gathering gathering;

    private boolean isHost;


    public GatheringMember(Long accountId, Role role, Gathering gathering, boolean isHost) {
        this.accountId = accountId;
        this.role = role;
        this.gathering = gathering;
        this.isHost = isHost;
    }



}
