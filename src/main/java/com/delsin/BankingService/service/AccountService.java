package com.delsin.BankingService.service;

import com.delsin.BankingService.model.Account;
import com.delsin.BankingService.model.Transaction;
import com.delsin.BankingService.model.User;

import java.math.BigDecimal;

public interface AccountService {

    Account createAccount(User user, BigDecimal balance);
    Account updateAccount(Account account);
    void deleteAccount(String email);
    void increaseBalance(double percent);
    //??

}
