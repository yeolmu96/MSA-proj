package sp.informationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sp.informationservice.entity.Institution;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    boolean existsByName(String name);
}
