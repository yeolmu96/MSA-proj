package com.msa.gathering.repository;

import com.msa.gathering.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    List<Notification> findByAccountIdAndIsReadFalseOrderByCreatedAtDesc(Long accountId);
    
    List<Notification> findByAccountIdOrderByCreatedAtDesc(Long accountId);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.accountId = :accountId AND n.isRead = false")
    int countUnreadNotifications(@Param("accountId") Long accountId);
}
