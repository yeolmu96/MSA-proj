package sp.qna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sp.qna_service.entity.Question;
import sp.qna_service.enums.NcsType;
import sp.qna_service.enums.QuestionType;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    List<Question> findAllByOrderByCreatedAtDesc();
    
    List<Question> findByNcsTypeOrderByCreatedAtDesc(NcsType ncsType);
    
    List<Question> findByQuestionTypeOrderByCreatedAtDesc(QuestionType questionType);
    
    List<Question> findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(String keyword);
    
    List<Question> findByAuthorIdOrderByCreatedAtDesc(Long authorId);
}
