package com.delsin.BankingService.service.impl;

import com.delsin.BankingService.model.ConstantValues;
import com.delsin.BankingService.model.entity.Account;
import com.delsin.BankingService.model.entity.User;
import com.delsin.BankingService.repository.AccountRepository;
import com.delsin.BankingService.repository.UserRepository;
import com.delsin.BankingService.security.MyUserDetails;
import com.delsin.BankingService.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

@Scheduled(fixedRate = 60000)
public void increaseBalanceOnInterest() {
    List<Account> accountList = accountRepository.findAll().stream()
            .filter(Account::isActiveDepositIncrease)
            .toList();
    for (Account account : accountList) {
        if (isEnoughPayments(account)) {
            account.setActiveDepositIncrease(false);
        } else {
            addOneTimePayment(account);
            account.setAccruedInterest(account
                    .getAccruedInterest()
                    .add(OneTimePayment(account))
            );
        }
        accountRepository.save(account);
    }
}

    private boolean isEnoughPayments(Account account) {
        BigDecimal maxPayout = account
                .getDownPayment()
                .multiply(ConstantValues.MAX_INTEREST_ON_BANK_DEPOSIT);
        BigDecimal accruedInterestUpd = account
                .getAccruedInterest()
                .add(OneTimePayment(account));
        return accruedInterestUpd.compareTo(maxPayout) > 0;
    }
    private BigDecimal OneTimePayment(Account account) {
        return account.getBalance()
                .multiply(ConstantValues.INTEREST_ON_BANK_DEPOSIT);
    }
    private void addOneTimePayment(Account account) {
        account.setBalance(account
                .getBalance()
                .add(OneTimePayment(account)));
    }
    private boolean isEnoughMoney(Account account, BigDecimal amount) {
        return account.getBalance().compareTo(amount) >= 0;
    }

}
