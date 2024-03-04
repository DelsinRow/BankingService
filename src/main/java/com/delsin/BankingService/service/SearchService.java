package com.delsin.BankingService.service;

import com.delsin.BankingService.model.User;

import java.time.LocalDate;

public interface SearchService {
    User findByBirthday(LocalDate birthday);
    User findByPhone(String phone);
    User findByFullName(String fullName);
    User findByEmail(String email);
}
