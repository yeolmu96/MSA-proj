package com.msa.gathering.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "gathering_id")
    private Long gatheringId;

    @Column(name = "application_id")
    private Long applicationId;

    private String message;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Notification(Long accountId, Long gatheringId, Long applicationId, String message) {
        this.accountId = accountId;
        this.gatheringId = gatheringId;
        this.applicationId = applicationId;
        this.message = message;
        this.isRead = false;
        this.createdAt = LocalDateTime.now();
    }

    public void markAsRead() {
        this.isRead = true;
    }
}
