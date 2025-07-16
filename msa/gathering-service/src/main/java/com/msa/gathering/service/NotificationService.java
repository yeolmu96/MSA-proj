package com.msa.gathering.service;

import com.msa.gathering.controller.response.NotificationResponse;
import com.msa.gathering.controller.response.NotificationStatusResponse;
import com.msa.gathering.entity.Notification;

import java.util.List;

public interface NotificationService {
    
    void createApplicationNotification(Long hostId, Long gatheringId, Long applicationId, String applicantName);
    
    void createApplicationStatusNotification(Long applicantId, Long gatheringId, Long applicationId, boolean isApproved);
    
    void createNotification(Long accountId, String title, String message, String link);
    
    NotificationStatusResponse getNotifications(Long accountId);
    
    void markAsRead(Long notificationId);
    
    int getUnreadCount(Long accountId);
}
