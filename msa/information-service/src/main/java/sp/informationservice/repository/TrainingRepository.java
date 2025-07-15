package sp.informationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sp.informationservice.entity.Training;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    boolean existsByName(String name);
}
