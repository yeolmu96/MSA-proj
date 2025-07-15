package sp.qna_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sp.qna_service.controller.request.AnswerRequest;
import sp.qna_service.controller.response.ApiResponse;
import sp.qna_service.controller.response.AnswerResponse;
import sp.qna_service.service.AnswerService;

import java.util.List;

@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {
    
    private final AnswerService answerService;
    
    @GetMapping("/question/{questionId}")
    public ResponseEntity<ApiResponse<List<AnswerResponse>>> getAnswersByQuestionId(
            @PathVariable Long questionId) {
        ApiResponse<List<AnswerResponse>> response = answerService.getAnswersByQuestionId(questionId);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/question/{questionId}")
    public ResponseEntity<ApiResponse<AnswerResponse>> createAnswer(
            @RequestHeader("Authorization") String token,
            @PathVariable Long questionId,
            @Valid @RequestBody AnswerRequest request) {
        ApiResponse<AnswerResponse> response = answerService.createAnswer(token, questionId, request);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{answerId}")
    public ResponseEntity<ApiResponse<AnswerResponse>> updateAnswer(
            @RequestHeader("Authorization") String token,
            @PathVariable Long answerId,
            @Valid @RequestBody AnswerRequest request) {
        ApiResponse<AnswerResponse> response = answerService.updateAnswer(token, answerId, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{answerId}")
    public ResponseEntity<ApiResponse<String>> deleteAnswer(
            @RequestHeader("Authorization") String token,
            @PathVariable Long answerId) {
        ApiResponse<String> response = answerService.deleteAnswer(token, answerId);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{parentAnswerId}/reply")
    public ResponseEntity<ApiResponse<AnswerResponse>> createReply(
            @RequestHeader("Authorization") String token,
            @PathVariable Long parentAnswerId,
            @Valid @RequestBody AnswerRequest request) {
        ApiResponse<AnswerResponse> response = answerService.createReply(token, parentAnswerId, request);
        return ResponseEntity.ok(response);
    }
}