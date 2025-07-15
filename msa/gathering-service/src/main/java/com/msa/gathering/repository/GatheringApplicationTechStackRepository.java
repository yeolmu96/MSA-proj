package com.msa.gathering.repository;

import com.msa.gathering.entity.GatheringTechStack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatheringApplicationTechStackRepository extends JpaRepository<GatheringTechStack, Long> {
}
