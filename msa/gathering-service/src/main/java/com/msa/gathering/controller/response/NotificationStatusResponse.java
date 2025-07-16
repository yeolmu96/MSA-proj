package com.msa.gathering.controller.response;

import lombok.Getter;

import java.util.List;

@Getter
public class NotificationStatusResponse {
    private int unreadCount;
    private List<NotificationResponse> notifications;

    public NotificationStatusResponse(int unreadCount, List<NotificationResponse> notifications) {
        this.unreadCount = unreadCount;
        this.notifications = notifications;
    }
}
