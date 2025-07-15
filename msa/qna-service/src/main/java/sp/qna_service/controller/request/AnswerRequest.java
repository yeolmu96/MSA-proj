package sp.qna_service.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequest {
    
    @NotBlank(message = "답변 내용은 필수입니다.")
    private String content;
    
    private Long parentAnswerId;
}
