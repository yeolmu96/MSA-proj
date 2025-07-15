package sp.qna_service.service;

import sp.qna_service.controller.request.AnswerRequest;
import sp.qna_service.controller.response.ApiResponse;
import sp.qna_service.controller.response.AnswerResponse;

import java.util.List;

public interface AnswerService {
    ApiResponse<List<AnswerResponse>> getAnswersByQuestionId(Long questionId);

    ApiResponse<AnswerResponse> createAnswer(String token, Long questionId, AnswerRequest request);

    ApiResponse<AnswerResponse> updateAnswer(String token, Long answerId, AnswerRequest request);

    ApiResponse<String> deleteAnswer(String token, Long answerId);

    ApiResponse<AnswerResponse> createReply(String token, Long parentAnswerId, AnswerRequest request);
}
