package com.msa.gathering.repository;

import com.msa.gathering.entity.Gathering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatheringRepository extends JpaRepository<Gathering, Long> {

}
