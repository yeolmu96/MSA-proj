package com.msa.gathering.repository;

import com.msa.gathering.entity.GatheringApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GatheringApplicationRepository extends JpaRepository<GatheringApplication, Long> {
    List<GatheringApplication> findByAccountId(Long accountId);
    List<GatheringApplication> findByGatheringId(Long gatheringId);
    void deleteByGatheringId(Long gatheringId);
}
