package com.msa.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
@Table(name = "msa_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

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
    }

    public Account(String userId, String password, String nickname, String company, Long point, LocalDateTime createdAt) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.company = company;
        this.point = point;
        this.createdAt = createdAt;
    }
}
