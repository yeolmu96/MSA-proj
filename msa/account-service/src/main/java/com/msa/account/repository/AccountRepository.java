package com.msa.account.repository;

import com.msa.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long accountId);
    Optional<Account> findByUserId(String userId);
    boolean existsByUserId(String userId);
}
