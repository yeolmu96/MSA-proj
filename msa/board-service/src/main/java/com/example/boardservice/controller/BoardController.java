package com.example.boardservice.controller;

import com.example.boardservice.entity.Board;
import com.example.boardservice.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;

    @PostMapping
    public Board createBoard(@RequestBody Board board) {
        return boardRepository.save(board);
    }

    @GetMapping
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }
}
