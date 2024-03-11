package com.delsin.BankingService.service;

import com.delsin.BankingService.security.MyUserDetails;

import java.math.BigDecimal;

public interface AccountService {

    void moneyTransfer(MyUserDetails userDetails, Long recipientId, BigDecimal amount);

    void increaseBalanceOnInterest();

}
