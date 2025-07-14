package com.example.boardservice.controller;

import com.example.boardservice.controller.request.PostRequest;
import com.example.boardservice.entity.Board;
import com.example.boardservice.entity.Post;
import com.example.boardservice.repository.BoardRepository;
import com.example.boardservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    // 특정 게시판에 글 작성
    @PostMapping("/create")
    public Post createPost(@RequestBody PostRequest request) {
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board not found"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setBoard(board); // 게시판 연관관계 설정

        return postRepository.save(post);
    }

    // 게시판에 속한 글 목록 가져오기
    @GetMapping("/board/{boardId}")
    public List<Post> getPostsByBoard(@PathVariable Long boardId) {
        return postRepository.findByBoardId(boardId);
    }

    // 게시글 조회수
    @GetMapping("/{id}")
    public Post getPost(@PathVariable Long id) {
        Optional<Post> maybePost = postRepository.findById(id);

        if (maybePost.isEmpty()) {
            throw new RuntimeException("게시글을 찾을 수 없습니다.");
        }

        Post post = maybePost.get();
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);

        return post;
    }

    // 추천 API 만들기
    @PostMapping("/{id}/recommend")
    public Post recommendPost(@PathVariable Long id) {
        Optional<Post> maybePost = postRepository.findById(id);

        if (maybePost.isEmpty()) {
            throw new RuntimeException("게시글을 찾을 수 없습니다.");
        }

        Post post = maybePost.get();
        post.setRecommendCount(post.getRecommendCount() + 1);
        postRepository.save(post);

        return post;
    }

    // Category 생성
    @PostMapping("/posts")
    public Post categoryPost(@RequestBody PostRequest request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCategory(request.getCategory());

        return postRepository.save(post);
    }

}
