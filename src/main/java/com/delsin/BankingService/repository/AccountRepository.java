package com.delsin.BankingService.repository;

import com.delsin.BankingService.model.entity.Account;
import com.delsin.BankingService.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long id);

    Optional<Account> findByUser(User user);
}
