package com.msa.gathering.repository;

import com.msa.gathering.entity.GatheringApplication;
import com.msa.gathering.entity.GatheringApplicationTechStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GatheringApplicationTechStackRepository extends JpaRepository<GatheringApplicationTechStack, Long> {
    List<GatheringApplicationTechStack> findByGatheringApplicationId(Long applicationId);
    void deleteByGatheringApplicationId(Long applicationId);
}
