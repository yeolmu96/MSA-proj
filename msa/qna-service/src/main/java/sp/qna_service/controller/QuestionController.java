package sp.qna_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sp.qna_service.controller.request.QuestionRequest;
import sp.qna_service.controller.response.ApiResponse;
import sp.qna_service.controller.response.QuestionResponse;
import sp.qna_service.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {
    
    private final QuestionService questionService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<QuestionResponse>> createQuestion(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody QuestionRequest request) {
        ApiResponse<QuestionResponse> response = questionService.createQuestion(token, request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionResponse>>> getAllQuestions() {
        ApiResponse<List<QuestionResponse>> response = questionService.getAllQuestions();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuestionResponse>> getQuestionById(@PathVariable Long id) {
        ApiResponse<QuestionResponse> response = questionService.getQuestion(id);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<QuestionResponse>> updateQuestion(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @Valid @RequestBody QuestionRequest request) {
        ApiResponse<QuestionResponse> response = questionService.updateQuestion(token, id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteQuestion(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        ApiResponse<String> response = questionService.deleteQuestion(token, id);
        return ResponseEntity.ok(response);
    }
}