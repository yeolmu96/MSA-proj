package sp.qna_service.service;

import sp.qna_service.controller.request.QuestionRequest;
import sp.qna_service.controller.response.ApiResponse;
import sp.qna_service.controller.response.QuestionResponse;

import java.util.List;

public interface QuestionService {
    ApiResponse<List<QuestionResponse>> getAllQuestions();

    ApiResponse<QuestionResponse> getQuestion(Long id);

    ApiResponse<QuestionResponse> createQuestion(String token, QuestionRequest request);

    ApiResponse<QuestionResponse> updateQuestion(String token, Long id, QuestionRequest request);

    ApiResponse<String> deleteQuestion(String token, Long id);
}
