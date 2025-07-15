package com.msa.gathering.repository;

import com.msa.gathering.entity.GatheringRole;
import com.msa.gathering.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatheringRoleRepository extends JpaRepository<GatheringRole, Long> {
    int role(Role role);
}
