package com.delsin.BankingService.repository;

import com.delsin.BankingService.model.entity.Phone;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean existsByPhone(String phone);
}
