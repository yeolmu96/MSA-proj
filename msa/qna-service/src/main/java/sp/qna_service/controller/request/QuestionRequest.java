package sp.qna_service.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sp.qna_service.enums.NcsType;
import sp.qna_service.enums.QuestionType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {
    
    @NotBlank(message = "제목은 필수입니다.")
    private String title;
    
    @NotBlank(message = "내용은 필수입니다.")
    private String content;
    
    private NcsType ncsType;
    
    @NotNull(message = "질문 유형은 필수입니다.")
    private QuestionType questionType;
}
