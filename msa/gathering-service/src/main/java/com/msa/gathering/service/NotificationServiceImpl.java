package com.msa.gathering.service;

import com.msa.gathering.controller.response.NotificationResponse;
import com.msa.gathering.controller.response.NotificationStatusResponse;
import com.msa.gathering.entity.Notification;
import com.msa.gathering.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void createApplicationNotification(Long hostId, Long gatheringId, Long applicationId, String applicantName) {
        String message = applicantName + "님이 모임 참가를 신청했습니다.";
        Notification notification = new Notification(hostId, gatheringId, applicationId, message);
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void createApplicationStatusNotification(Long applicantId, Long gatheringId, Long applicationId, boolean isApproved) {
        String message = isApproved ? 
                "모임 참가 신청이 승인되었습니다." : 
                "모임 참가 신청이 거절되었습니다.";
        Notification notification = new Notification(applicantId, gatheringId, applicationId, message);
        notificationRepository.save(notification);
    }

    @Override
    public void createNotification(Long accountId, String title, String message, String link) {

    }

    @Override
    @Transactional(readOnly = true)
    public NotificationStatusResponse getNotifications(Long accountId) {
        List<Notification> notifications = notificationRepository.findByAccountIdOrderByCreatedAtDesc(accountId);
        List<NotificationResponse> notificationResponses = notifications.stream()
                .map(NotificationResponse::from)
                .collect(Collectors.toList());
        
        int unreadCount = notificationRepository.countUnreadNotifications(accountId);
        
        return new NotificationStatusResponse(unreadCount, notificationResponses);
    }

    @Override
    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("알림을 찾을 수 없습니다."));
        notification.markAsRead();
        notificationRepository.save(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public int getUnreadCount(Long accountId) {
        return notificationRepository.countUnreadNotifications(accountId);
    }
}
