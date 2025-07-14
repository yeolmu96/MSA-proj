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

    @Column(name = "view_count")
    private int viewCount = 0;

    @Column(name = "recommend_count")
    @Builder.Default
    private Integer  recommendCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private Board board;

    @Enumerated(EnumType.STRING)
    private Category category;

    public Post() {
    }

    public Post(String title, String content, int viewCount, int recommendCount, Board board) {
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.recommendCount = recommendCount;
        this.board = board;
    }
}
