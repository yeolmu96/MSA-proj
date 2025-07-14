package com.msa.gathering.repository;

import com.msa.gathering.entity.TechStack;
import com.msa.gathering.entity.TechStacks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {
    boolean existsByTechStack(TechStacks techStack);

}
