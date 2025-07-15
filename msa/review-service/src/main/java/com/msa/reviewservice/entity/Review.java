package com.msa.reviewservice.entity;

import com.msa.reviewservice.controller.response.NcsType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;
    private String nickname;
    private Long trainingId;
    private String trainingName;
    private int trainingPeriod;
    private NcsType trainingType;
    private String title;
    private String content;
    private Integer rating;
    private String imagePath;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Review(Long accountId, String nickname, Long trainingId, String trainingName, int trainingPeriod, NcsType trainingType, String title, String content, Integer rating, String imagePath) {
        this.accountId = accountId;
        this.nickname = nickname;
        this.trainingId = trainingId;
        this.trainingName = trainingName;
        this.trainingPeriod = trainingPeriod;
        this.trainingType = trainingType;
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.imagePath = imagePath;
    }
}
