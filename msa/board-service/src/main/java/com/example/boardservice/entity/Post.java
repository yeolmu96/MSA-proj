package com.example.boardservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private int recommendCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

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
