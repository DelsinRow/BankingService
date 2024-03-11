package com.delsin.BankingService.service.impl;

import com.delsin.BankingService.model.ConstantValues;
import com.delsin.BankingService.model.entity.Account;
import com.delsin.BankingService.model.entity.Transaction;
import com.delsin.BankingService.model.entity.User;
import com.delsin.BankingService.repository.AccountRepository;
import com.delsin.BankingService.repository.TransactionRepository;
import com.delsin.BankingService.repository.UserRepository;
import com.delsin.BankingService.security.MyUserDetails;
import com.delsin.BankingService.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    @Transactional
    public void moneyTransfer(MyUserDetails userDetails, Long recipientId, BigDecimal amount) {
        logger.info("Transaction has started");
        String login = userDetails.getUsername();
        User senderUser = userRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(login + " not exist"));
        Account senderAccount = accountRepository.findByUser(senderUser)
                .orElseThrow(() -> new EntityNotFoundException("Account by user: " + senderUser.getLogin() + " not found"));
        if (!isEnoughMoney(senderAccount, amount)) {
            logger.info("Transaction failed. Not enough money in the account ID: " + senderAccount.getId());
            throw new IllegalArgumentException("Insufficient funds in the account");
        } else {
            Account recipientAccount = accountRepository.findById(recipientId)
                    .orElseThrow(() -> new EntityNotFoundException("Account with id:" + recipientId + " not found."));
            senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
            recipientAccount.setBalance(recipientAccount.getBalance().add(amount));
            logger.info("Transaction recording. ID: " + senderAccount.getId() + " -> " + recipientAccount.getId());
            Transaction transaction = Transaction.builder()
                    .senderId(senderAccount.getId())
                    .recipientId(recipientId)
                    .amount(amount)
                    .transactionDate(LocalDate.now())
                    .build();

            transactionRepository.save(transaction);
            accountRepository.save(senderAccount);
            accountRepository.save(recipientAccount);
            logger.info("Transaction completed. ID: " + senderAccount.getId() + " -> " + recipientAccount.getId() + ". Amount: " + amount);
        }
    }


    @Override
    @Scheduled(fixedRate = 60000)
    public void increaseBalanceOnInterest() {
        logger.info("Interest accrual");
        List<Account> accountList = accountRepository.findAll().stream()
                .filter(Account::isActiveDepositIncrease)
                .toList();
        for (Account account : accountList) {
            if (isEnoughPayments(account)) {
                account.setActiveDepositIncrease(false);
            } else {
                account.setAccruedInterest(account
                        .getAccruedInterest()
                        .add(OneTimePayment(account))
                );
                addOneTimePayment(account);
            }
            accountRepository.save(account);
            logger.info("Interest accrued");
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
