package com.delsin.BankingService.repository;

import com.delsin.BankingService.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
    boolean existsByEmail(String email);
}
