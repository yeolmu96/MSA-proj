package sp.qna_service.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sp.qna_service.entity.Answer;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerResponse {
    private Long id;
    private Long questionId;
    private String content;
    private Long authorId;
    private String authorNickname;
    private Long parentAnswerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<AnswerResponse> replies;

    public static AnswerResponse from(Answer answer) {
        return AnswerResponse.builder()
                .id(answer.getId())
                .questionId(answer.getQuestion().getId())
                .content(answer.getContent())
                .authorId(answer.getAuthorId())
                .authorNickname(answer.getAuthorNickname())
                .parentAnswerId(answer.getParentAnswerId())
                .createdAt(answer.getCreatedAt())
                .updatedAt(answer.getUpdatedAt())
                .build();
    }

    public static AnswerResponse fromWithReplies(Answer answer, List<AnswerResponse> replies) {
        return AnswerResponse.builder()
                .id(answer.getId())
                .questionId(answer.getQuestion().getId())
                .content(answer.getContent())
                .authorId(answer.getAuthorId())
                .authorNickname(answer.getAuthorNickname())
                .parentAnswerId(answer.getParentAnswerId())
                .createdAt(answer.getCreatedAt())
                .updatedAt(answer.getUpdatedAt())
                .replies(replies)
                .build();
    }
}