package com.msa.reviewservice.repository;

import com.msa.reviewservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByAccountId(Long id);
    List<Review> findByTrainingName(String trainingName);
}
