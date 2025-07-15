package sp.qna_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sp.qna_service.client.AccountClient;
import sp.qna_service.controller.request.AnswerRequest;
import sp.qna_service.controller.response.ApiResponse;
import sp.qna_service.controller.response.AnswerResponse;
import sp.qna_service.controller.response.MyAccountInfoResponse;
import sp.qna_service.entity.Answer;
import sp.qna_service.entity.Question;
import sp.qna_service.repository.AnswerRepository;
import sp.qna_service.repository.QuestionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final AccountClient accountClient;

    @Override
    public ApiResponse<List<AnswerResponse>> getAnswersByQuestionId(Long questionId) {
        try {
            List<Answer> parentAnswers = answerRepository.findByQuestionIdAndParentAnswerIdIsNullOrderByCreatedAtAsc(questionId);

            List<AnswerResponse> responses = parentAnswers.stream()
                    .map(parentAnswer -> {
                        List<Answer> replies = answerRepository.findByParentAnswerIdOrderByCreatedAtAsc(parentAnswer.getId());
                        List<AnswerResponse> replyResponses = replies.stream()
                                .map(AnswerResponse::from)
                                .collect(Collectors.toList());

                        return AnswerResponse.fromWithReplies(parentAnswer, replyResponses);
                    })
                    .collect(Collectors.toList());

            return ApiResponse.success("답변 목록을 성공적으로 조회했습니다", responses);
        } catch (Exception e) {
            return ApiResponse.failure("답변 목록 조회에 실패했습니다");
        }
    }

    @Override
    @Transactional
    public ApiResponse<AnswerResponse> createAnswer(String token, Long questionId, AnswerRequest request) {
        try {
            MyAccountInfoResponse userInfo = accountClient.getMe(token);

            Question question = questionRepository.findById(questionId)
                    .orElseThrow(() -> new RuntimeException("질문을 찾을 수 없습니다."));

            Answer answer = Answer.builder()
                    .question(question)
                    .content(request.getContent())
                    .authorId(userInfo.getAccountId())
                    .authorNickname(userInfo.getNickname())
                    .parentAnswerId(null)
                    .build();

            Answer savedAnswer = answerRepository.save(answer);
            AnswerResponse response = AnswerResponse.from(savedAnswer);

            return ApiResponse.success("답변이 성공적으로 작성되었습니다", response);
        } catch (Exception e) {
            return ApiResponse.failure("답변 작성에 실패했습니다");
        }
    }

    @Override
    @Transactional
    public ApiResponse<AnswerResponse> updateAnswer(String token, Long answerId, AnswerRequest request) {
        try {
            MyAccountInfoResponse userInfo = accountClient.getMe(token);

            Answer answer = answerRepository.findById(answerId)
                    .orElseThrow(() -> new RuntimeException("답변을 찾을 수 없습니다."));

            if (!answer.getAuthorId().equals(userInfo.getAccountId())) {
                return ApiResponse.failure("답변을 수정할 권한이 없습니다");
            }

            Answer updatedAnswer = Answer.builder()
                    .id(answer.getId())
                    .question(answer.getQuestion())
                    .content(request.getContent())
                    .authorId(answer.getAuthorId())
                    .authorNickname(answer.getAuthorNickname())
                    .parentAnswerId(answer.getParentAnswerId())
                    .createdAt(answer.getCreatedAt())
                    .build();

            Answer savedAnswer = answerRepository.save(updatedAnswer);
            AnswerResponse response = AnswerResponse.from(savedAnswer);

            return ApiResponse.success("답변이 성공적으로 수정되었습니다", response);
        } catch (Exception e) {
            return ApiResponse.failure("답변 수정에 실패했습니다");
        }
    }

    @Override
    @Transactional
    public ApiResponse<String> deleteAnswer(String token, Long answerId) {
        try {
            MyAccountInfoResponse userInfo = accountClient.getMe(token);

            Answer answer = answerRepository.findById(answerId)
                    .orElseThrow(() -> new RuntimeException("답변을 찾을 수 없습니다."));

            if (!answer.getAuthorId().equals(userInfo.getAccountId())) {
                return ApiResponse.failure("답변을 삭제할 권한이 없습니다");
            }

            // 대댓글이 있다면 함께 삭제
            answerRepository.deleteByParentAnswerId(answerId);
            
            // 답변 물리적 삭제
            answerRepository.delete(answer);

            return ApiResponse.success("답변이 성공적으로 삭제되었습니다", "삭제 완료");
        } catch (Exception e) {
            return ApiResponse.failure("답변 삭제에 실패했습니다");
        }
    }

    @Override
    @Transactional
    public ApiResponse<AnswerResponse> createReply(String token, Long parentAnswerId, AnswerRequest request) {
        try {
            MyAccountInfoResponse userInfo = accountClient.getMe(token);

            Answer parentAnswer = answerRepository.findById(parentAnswerId)
                    .orElseThrow(() -> new RuntimeException("부모 답변을 찾을 수 없습니다."));

            Answer reply = Answer.builder()
                    .question(parentAnswer.getQuestion())
                    .content(request.getContent())
                    .authorId(userInfo.getAccountId())
                    .authorNickname(userInfo.getNickname())
                    .parentAnswerId(parentAnswerId)
                    .build();

            Answer savedReply = answerRepository.save(reply);
            AnswerResponse response = AnswerResponse.from(savedReply);

            return ApiResponse.success("대댓글이 성공적으로 작성되었습니다", response);
        } catch (Exception e) {
            return ApiResponse.failure("대댓글 작성에 실패했습니다");
        }
    }
}
