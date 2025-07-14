package com.msa.account.repository;

import com.msa.account.entitiy.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserId(String userId);
}
