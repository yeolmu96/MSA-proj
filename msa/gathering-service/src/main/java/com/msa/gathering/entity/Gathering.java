package com.msa.gathering.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Gathering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "host_id")
    private Long hostId;

    @Column(name = "team_name")
    private String teamName;

    private String title;

    private String description;

    @Column(name = "max_member_count", nullable = false)
    private int maxMemberCount;

    @Column(name = "current_member_count", nullable = false)
    private int currentMemberCount = 1;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GatheringStatus status = GatheringStatus.RECRUITING;


    public void setCurrentMemberCount(int currentMemberCount) {
        this.currentMemberCount = currentMemberCount;
    }
    
    public void update(String teamName, String title, String description, int maxMemberCount) {
        this.teamName = teamName;
        this.title = title;
        this.description = description;
        this.maxMemberCount = maxMemberCount;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void startGathering() {
        this.status = GatheringStatus.STARTED;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void completeGathering() {
        this.status = GatheringStatus.COMPLETED;
        this.updatedAt = LocalDateTime.now();
    }

    public Gathering(Long hostId, String teamName, String title, String description, int maxMemberCount, int currentMemberCount) {
        this.hostId = hostId;
        this.teamName = teamName;
        this.title = title;
        this.description = description;
        this.maxMemberCount = maxMemberCount;
        this.currentMemberCount = currentMemberCount;
        this.createdAt = LocalDateTime.now();
    }
}
