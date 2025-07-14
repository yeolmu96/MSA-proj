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
