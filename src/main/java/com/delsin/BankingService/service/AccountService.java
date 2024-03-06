package com.delsin.BankingService.service;

import com.delsin.BankingService.model.Account;
import com.delsin.BankingService.model.Transaction;
import com.delsin.BankingService.model.User;
import com.delsin.BankingService.security.MyUserDetails;

import java.math.BigDecimal;

public interface AccountService {

    void moneyTransfer(MyUserDetails userDetails, Long recipientId, BigDecimal amount);
//    void increaseBalance(Long accountId, BigDecimal interest);

}
