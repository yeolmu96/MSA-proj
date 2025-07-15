package sp.qna_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sp.qna_service.client.AccountClient;
import sp.qna_service.controller.request.QuestionRequest;
import sp.qna_service.controller.response.ApiResponse;
import sp.qna_service.controller.response.MyAccountInfoResponse;
import sp.qna_service.controller.response.QuestionResponse;
import sp.qna_service.entity.Question;
import sp.qna_service.repository.AnswerRepository;
import sp.qna_service.repository.QuestionRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final AccountClient accountClient;

    @Override
    public ApiResponse<List<QuestionResponse>> getAllQuestions() {
        try {
            List<Question> questions = questionRepository.findAllByOrderByCreatedAtDesc();

            List<QuestionResponse> responses = questions.stream()
                    .map(question -> {
                        Long answerCount = answerRepository.countByQuestionId(question.getId());
                        return QuestionResponse.fromWithAnswerCount(question, answerCount);
                    })
                    .toList();

            return ApiResponse.success("질문 목록을 성공적으로 조회했습니다", responses);
        } catch (Exception e) {
            return ApiResponse.failure("질문 목록 조회에 실패했습니다");
        }
    }

    @Override
    @Transactional
    public ApiResponse<QuestionResponse> getQuestion(Long id) {
        try {
            Question question = questionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("질문을 찾을 수 없습니다."));

            question.incrementViewCount();
            questionRepository.save(question);

            Long answerCount = answerRepository.countByQuestionId(question.getId());
            QuestionResponse response = QuestionResponse.fromWithAnswerCount(question, answerCount);

            return ApiResponse.success("질문을 성공적으로 조회했습니다", response);
        } catch (Exception e) {
            return ApiResponse.failure("질문 조회에 실패했습니다");
        }
    }

    @Override
    @Transactional
    public ApiResponse<QuestionResponse> createQuestion(String token, QuestionRequest request) {
        try {
            MyAccountInfoResponse userInfo = accountClient.getMe(token);

            Question question = Question.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .authorId(userInfo.getAccountId())
                    .authorNickname(userInfo.getNickname())
                    .ncsType(request.getNcsType())
                    .questionType(request.getQuestionType())
                    .build();

            Question savedQuestion = questionRepository.save(question);
            QuestionResponse response = QuestionResponse.from(savedQuestion);

            return ApiResponse.success("질문이 성공적으로 작성되었습니다", response);
        } catch (Exception e) {
            return ApiResponse.failure("질문 작성에 실패했습니다");
        }
    }

    @Override
    @Transactional
    public ApiResponse<QuestionResponse> updateQuestion(String token, Long id, QuestionRequest request) {
        try {
            MyAccountInfoResponse userInfo = accountClient.getMe(token);

            Question question = questionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("질문을 찾을 수 없습니다."));

            if (!question.getAuthorId().equals(userInfo.getAccountId())) {
                return ApiResponse.failure("질문을 수정할 권한이 없습니다");
            }

            Question updatedQuestion = Question.builder()
                    .id(question.getId())
                    .title(request.getTitle())
                    .content(request.getContent())
                    .authorId(question.getAuthorId())
                    .authorNickname(question.getAuthorNickname())
                    .ncsType(request.getNcsType())
                    .questionType(request.getQuestionType())
                    .viewCount(question.getViewCount())
                    .createdAt(question.getCreatedAt())
                    .build();

            Question savedQuestion = questionRepository.save(updatedQuestion);
            QuestionResponse response = QuestionResponse.from(savedQuestion);

            return ApiResponse.success("질문이 성공적으로 수정되었습니다", response);
        } catch (Exception e) {
            return ApiResponse.failure("질문 수정에 실패했습니다");
        }
    }

    @Override
    @Transactional
    public ApiResponse<String> deleteQuestion(String token, Long id) {
        try {
            MyAccountInfoResponse userInfo = accountClient.getMe(token);

            Question question = questionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("질문을 찾을 수 없습니다."));

            if (!question.getAuthorId().equals(userInfo.getAccountId())) {
                return ApiResponse.failure("질문을 삭제할 권한이 없습니다");
            }

            // 해당 질문의 모든 답변도 함께 삭제
            answerRepository.deleteByQuestionId(id);
            
            // 질문 물리적 삭제
            questionRepository.delete(question);

            return ApiResponse.success("질문이 성공적으로 삭제되었습니다", "삭제 완료");
        } catch (Exception e) {
            return ApiResponse.failure("질문 삭제에 실패했습니다");
        }
    }
}
