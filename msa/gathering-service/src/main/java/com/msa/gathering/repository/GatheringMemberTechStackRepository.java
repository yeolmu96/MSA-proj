package com.msa.gathering.repository;

import com.msa.gathering.entity.GatheringMemberTechStack;
import org.springframework.data.repository.CrudRepository;

public interface GatheringMemberTechStackRepository extends CrudRepository<GatheringMemberTechStack, Long> {
    void deleteByGatheringMemberId(Long gatheringMemberId);
}
