package com.example.boardservice.repository;

import com.example.boardservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBoardId(Long boardId);

    Optional<Post> findByTitle(String title);

    List<Post> findByRecommendCountGreaterThanEqualOrderByRecommendCountDesc(int count);
}
