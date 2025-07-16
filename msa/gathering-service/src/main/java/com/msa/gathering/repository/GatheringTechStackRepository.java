package com.msa.gathering.repository;

import com.msa.gathering.entity.GatheringTechStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GatheringTechStackRepository extends JpaRepository<GatheringTechStack, Long> {
    List<GatheringTechStack> findByGatheringId(Long gatheringId);
    List<GatheringTechStack> findByGatheringIdIn(List<Long> gatheringIds);
    void deleteByGatheringId(Long gatheringId);
}
