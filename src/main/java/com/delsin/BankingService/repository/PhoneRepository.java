package com.delsin.BankingService.repository;

import com.delsin.BankingService.model.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    boolean existsByPhone(String phone);
}
