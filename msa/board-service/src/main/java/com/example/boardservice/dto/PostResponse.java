package com.example.boardservice.dto;

import com.example.boardservice.entity.Category;
import com.example.boardservice.entity.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private int viewCount;
    private Integer recommendCount;
    private Category category;
    private Long boardId;
    private String boardTitle;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.viewCount = post.getViewCount();
        this.recommendCount = post.getRecommendCount();
        this.category = post.getCategory();
        this.boardId = post.getBoard() != null ? post.getBoard().getId() : null;
        this.boardTitle = post.getBoard() != null ? post.getBoard().getTitle() : null;
    }
}