package com.msa.gathering.entity;

import com.msa.gathering.controller.request.GatheringApplicationRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class GatheringApplication {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "gathering_id")
    private Long gatheringId;

    private Role role;

    private String description;

    private Application isApproved;

    public GatheringApplication(Long accountId, Long gatheringId, Role role, String description) {
        this.accountId = accountId;
        this.gatheringId = gatheringId;
        this.role = role;
        this.description = description;
        this.isApproved = Application.PENDING;
    }

    public static GatheringApplication from(GatheringApplicationRequest req) {
        GatheringApplication gatheringApplication = new GatheringApplication(
                req.getAccountId(),
                req.getGatheringId(),
                req.getRole(),
                req.getDescription()
        );

        return gatheringApplication;
    }

    public void setApproved(Application approved) {
        this.isApproved = approved;
    }



}
