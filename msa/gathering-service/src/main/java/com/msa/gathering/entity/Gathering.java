package com.msa.gathering.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
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
    private int currentMemberCount;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;




}
