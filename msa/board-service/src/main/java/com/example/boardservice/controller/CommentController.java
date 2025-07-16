package com.example.boardservice.controller;

import com.example.boardservice.controller.request.CommentRequest;
import com.example.boardservice.entity.Comment;
import com.example.boardservice.entity.Post;
import com.example.boardservice.repository.BoardRepository;
import com.example.boardservice.repository.CommentRepository;
import com.example.boardservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping ("/board/comments")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    @PostMapping("/create")
    public Comment createComment(@RequestBody CommentRequest request) {
        Post post = postRepository.findById(request.getPostId()).orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment(request.getContent(), post);
        return commentRepository.save(comment);
    }

    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
