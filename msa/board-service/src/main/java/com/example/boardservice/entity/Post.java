package com.example.boardservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "view_count")
    private int viewCount = 0;

    @Column(name = "recommend_count")
    private Integer recommendCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Enumerated(EnumType.STRING)
    private Category category;

    public Post() {
    }

    @Builder
    public Post(String title, String content, int viewCount, int recommendCount, Board board, User user, Category category) {
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.recommendCount = recommendCount;
        this.board = board;
        this.category = category;
        this.user = user;
    }

    @PrePersist
    public void prePersist() {
        if (this.recommendCount == null) this.recommendCount = 0;
        if (this.viewCount == 0) this.viewCount = 0;
    }
}
