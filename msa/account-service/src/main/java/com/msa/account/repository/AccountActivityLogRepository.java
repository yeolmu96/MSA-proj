package com.msa.account.repository;

import com.msa.account.entity.AccountActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountActivityLogRepository extends JpaRepository<AccountActivityLog,Integer> {
}
