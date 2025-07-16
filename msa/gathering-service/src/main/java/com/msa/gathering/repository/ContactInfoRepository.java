package com.msa.gathering.repository;

import com.msa.gathering.entity.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {
    List<ContactInfo> findByGatheringId(Long gatheringId);
    Optional<ContactInfo> findByGatheringIdAndAccountId(Long gatheringId, Long accountId);
    void deleteByGatheringId(Long gatheringId);
}
