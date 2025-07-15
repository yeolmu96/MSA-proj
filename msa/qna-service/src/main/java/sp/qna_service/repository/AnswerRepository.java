package sp.qna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sp.qna_service.entity.Answer;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    
    Long countByQuestionId(Long questionId);

    List<Answer> findByParentAnswerIdOrderByCreatedAtAsc(Long parentAnswerId);

    List<Answer> findByQuestionIdAndParentAnswerIdIsNullOrderByCreatedAtAsc(Long questionId);
    
    void deleteByQuestionId(Long questionId);
    
    void deleteByParentAnswerId(Long parentAnswerId);
}
