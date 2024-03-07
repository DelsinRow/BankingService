package com.delsin.BankingService.service.impl;

import com.delsin.BankingService.model.entity.Account;
import com.delsin.BankingService.model.entity.User;
import com.delsin.BankingService.repository.AccountRepository;
import com.delsin.BankingService.repository.UserRepository;
import com.delsin.BankingService.security.MyUserDetails;
import com.delsin.BankingService.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void moneyTransfer(MyUserDetails userDetails, Long recipientId, BigDecimal amount) {
        String login = userDetails.getUsername();
        User senderUser = userRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(login + " not exist"));
        Account senderAccount = accountRepository.findByUser(senderUser)
                .orElseThrow(() -> new EntityNotFoundException(""));
        if (!isEnoughMoney(senderAccount, amount)) {
            throw new IllegalArgumentException("Insufficient funds in the account");
        } else {
            Account recipientAccount = accountRepository.findById(recipientId)
                    .orElseThrow(() -> new NoSuchElementException("Account with id: " + recipientId + " not found."));
            senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
            recipientAccount.setBalance(recipientAccount.getBalance().add(amount));
            //todo создание и сохранение транзакции
            accountRepository.save(senderAccount);
            accountRepository.save(recipientAccount);
        }
    }
//todo реализовать

//    @Override
//    @Scheduled(fixedRate = 60000)
//    public void increaseBalance(Long accountId, BigDecimal interest) {
//
//    }

    boolean isEnoughMoney(Account account, BigDecimal amount) {
        return account.getBalance().compareTo(amount) >= 0;
    }

}
