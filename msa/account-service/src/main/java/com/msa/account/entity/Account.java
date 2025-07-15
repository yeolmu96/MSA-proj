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
public class Account {

    //계정 고유 ID
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //로그인 ID
    @Column(name = "user_id", nullable = false)
    private String userId;

    //암호화 비밀번호
    @Column(nullable = false)
    private String password;

    //닉네임
    @Column(nullable = false)
    private String nickname;

    //속한 교육 코드
    @Column(name = "training_id", nullable = false)
    private Long trainingId;

    //포인트
    private Long point;

    //가입일자
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Account() {

    }

    public Account(String userId, String password, Long trainingId) {
        this.userId = userId;
        this.password = password;
        this.trainingId = trainingId;
    }

    public Account(String userId, String password, String nickname, Long trainingId, Long point, LocalDateTime createdAt) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.trainingId = trainingId;
        this.point = point;
        this.createdAt = createdAt;
    }
}
