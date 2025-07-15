package sp.qna_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import sp.qna_service.enums.NcsType;
import sp.qna_service.enums.QuestionType;

import java.time.LocalDateTime;

@Entity
@Table(name = "question")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "author_nickname", nullable = false)
    private String authorNickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "ncs_type")
    private NcsType ncsType;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false)
    private QuestionType questionType;

    @Column(name = "view_count", nullable = false)
    private Long viewCount;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (viewCount == null) {
            viewCount = 0L;
        }
        if (questionType == null) {
            questionType = QuestionType.GENERAL;
        }
    }

    public void incrementViewCount() {
        this.viewCount++;
    }
}
