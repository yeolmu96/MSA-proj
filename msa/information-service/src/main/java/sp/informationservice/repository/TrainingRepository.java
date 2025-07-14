package sp.informationservice.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import sp.informationservice.entity.Training;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    boolean existsByName(String name);
}
