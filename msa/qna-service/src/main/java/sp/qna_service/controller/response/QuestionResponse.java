package sp.qna_service.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sp.qna_service.entity.Question;
import sp.qna_service.enums.NcsType;
import sp.qna_service.enums.QuestionType;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponse {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private String authorNickname;
    private NcsType ncsType;
    private QuestionType questionType;
    private Long viewCount;
    private LocalDateTime createdAt;
    private Long answerCount;

    public static QuestionResponse from(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .authorId(question.getAuthorId())
                .authorNickname(question.getAuthorNickname())
                .ncsType(question.getNcsType())
                .questionType(question.getQuestionType())
                .viewCount(question.getViewCount())
                .createdAt(question.getCreatedAt())
                .build();
    }

    public static QuestionResponse fromWithAnswerCount(Question question, Long answerCount) {
        return QuestionResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .authorId(question.getAuthorId())
                .authorNickname(question.getAuthorNickname())
                .ncsType(question.getNcsType())
                .questionType(question.getQuestionType())
                .viewCount(question.getViewCount())
                .createdAt(question.getCreatedAt())
                .answerCount(answerCount)
                .build();
    }
}
