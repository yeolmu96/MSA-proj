package com.msa.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@ToString
@Table(name = "msa_account")
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    private String company;

    private Long point;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Account() {

    }

    public Account(String userId, String password) {
        this.userId = userId;
        this.password = password;
        this.nickname = generateRandomNickname();
    }

    public Account(String userId, String password, String nickname, String company, Long point, LocalDateTime createdAt) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.company = company;
        this.point = point;
        this.createdAt = createdAt;
    }

    private String generateRandomNickname() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
