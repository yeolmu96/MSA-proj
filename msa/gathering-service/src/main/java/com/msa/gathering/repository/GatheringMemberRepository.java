package com.msa.gathering.repository;

import com.msa.gathering.entity.GatheringMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GatheringMemberRepository extends JpaRepository<GatheringMember, Long> {
    List<GatheringMember> findByGatheringIdIn(List<Long> gatheringIds);
    List<GatheringMember> findByGatheringId(Long gatheringId);
    List<GatheringMember> findByAccountId(Long accountId);
    Optional<GatheringMember> findByGatheringIdAndAccountId(Long gatheringId, Long accountId);
    void deleteByGatheringId(Long gatheringId);
}
