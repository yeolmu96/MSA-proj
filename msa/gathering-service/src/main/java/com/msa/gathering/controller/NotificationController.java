package com.msa.gathering.controller;

import com.msa.gathering.controller.response.NotificationStatusResponse;
import com.msa.gathering.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gathering/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{accountId}")
    public ResponseEntity<NotificationStatusResponse> getNotifications(@PathVariable Long accountId) {
        NotificationStatusResponse notifications = notificationService.getNotifications(accountId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/unread-count/{accountId}")
    public ResponseEntity<Integer> getUnreadCount(@PathVariable Long accountId) {
        int unreadCount = notificationService.getUnreadCount(accountId);
        return ResponseEntity.ok(unreadCount);
    }

    @PutMapping("/read/{notificationId}")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok().build();
    }
}
