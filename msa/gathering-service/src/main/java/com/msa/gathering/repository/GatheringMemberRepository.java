package com.msa.gathering.repository;

import com.msa.gathering.entity.GatheringMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GatheringMemberRepository extends JpaRepository<GatheringMember, Long> {

    List<GatheringMember> findByGatheringIdIn(List<Long> gatheringIds);
}
