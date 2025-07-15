package com.msa.gathering.controller.response;

import com.msa.gathering.entity.Notification;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotificationResponse {
    private Long id;
    private Long gatheringId;
    private Long applicationId;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;

    public NotificationResponse(Long id, Long gatheringId, Long applicationId, String message, boolean isRead, LocalDateTime createdAt) {
        this.id = id;
        this.gatheringId = gatheringId;
        this.applicationId = applicationId;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public static NotificationResponse from(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getGatheringId(),
                notification.getApplicationId(),
                notification.getMessage(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }
}
