package com.msa.gathering.repository;

import com.msa.gathering.entity.GatheringRole;
import com.msa.gathering.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GatheringRoleRepository extends JpaRepository<GatheringRole, Long> {
    List<GatheringRole> findByGatheringId(Long gatheringId);
    List<GatheringRole> findByGatheringIdIn(List<Long> gatheringIds);
    void deleteByGatheringId(Long gatheringId);
}
