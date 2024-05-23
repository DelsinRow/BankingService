package com.delsin.BankingService.repository;

import com.delsin.BankingService.model.entity.Email;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean existsByEmail(String email);
}
