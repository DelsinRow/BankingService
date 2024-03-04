package com.delsin.BankingService.repository;

import com.delsin.BankingService.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
