package com.example.boardservice.controller;

import com.example.boardservice.controller.request.DeleteRequest;
import com.example.boardservice.controller.request.PostRequest;
import com.example.boardservice.controller.request.UpdateRequest;
import com.example.boardservice.dto.PostResponse;
import com.example.boardservice.entity.Board;
import com.example.boardservice.entity.Category;
import com.example.boardservice.entity.Post;
import com.example.boardservice.repository.BoardRepository;
import com.example.boardservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/board/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    // 특정 게시판에 글 작성
    @PostMapping("/create")
    public PostResponse createPost(@RequestBody PostRequest request) {
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board not found"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCategory(request.getCategory());
        post.setBoard(board); // 게시판 연관관계 설정

        Post savedPost = postRepository.save(post);
        return new PostResponse(savedPost);
    }

    // 게시판에 속한 글 목록 가져오기
    @GetMapping("/board/{boardId}")
    public List<PostResponse> getPostsByBoard(@PathVariable Long boardId) {
        return postRepository.findByBoardId(boardId)
                .stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    // 게시글 조회수
    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        Optional<Post> maybePost = postRepository.findById(id);

        if (maybePost.isEmpty()) {
            throw new RuntimeException("게시글을 찾을 수 없습니다.");
        }

        Post post = maybePost.get();
        post.setViewCount(post.getViewCount() + 1);
        Post savedPost = postRepository.save(post);

        return new PostResponse(savedPost);
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

    // 게시물 삭제
    @PostMapping("/delete")
    public String deletePost(@RequestBody DeleteRequest request) {
        postRepository.deleteById(request.getId());
        return "삭제되었습니다.";
    }

    // 게시물 업데이트
    @PostMapping("/update")
    public Post updatePost(@RequestBody UpdateRequest request) {
        String content = request.getContent();
        Category category = request.getCategory();
        Optional<Post> optionalPost = postRepository.findById(request.getId());

        if (optionalPost.isEmpty()) {
            return null;
        }
        Post post = optionalPost.get();
        post.setContent(content);
        post.setCategory(category);
        post.setTitle(request.getTitle());

        return postRepository.save(post);

    }

    @GetMapping("/best")
    public List<Post> getBestPosts() {
        return postRepository.findByRecommendCountGreaterThanEqualOrderByRecommendCountDesc(10);
    }
}
