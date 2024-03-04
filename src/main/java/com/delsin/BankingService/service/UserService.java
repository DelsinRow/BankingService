package com.delsin.BankingService.service;

import com.delsin.BankingService.model.Account;
import com.delsin.BankingService.model.User;

import java.time.LocalDate;

public interface UserService {
    void addEmail(String email);
    void updateEmail(String email);
    void deleteEmail(String email);

    void addPhone(String phone);
    void updatePhone(String phone);
    void deletePhone(String phone);




}
