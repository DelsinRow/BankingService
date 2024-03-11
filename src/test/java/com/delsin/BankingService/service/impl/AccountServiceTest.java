package com.delsin.BankingService.service.impl;

import com.delsin.BankingService.model.entity.Account;
import com.delsin.BankingService.model.entity.Transaction;
import com.delsin.BankingService.model.entity.User;
import com.delsin.BankingService.repository.AccountRepository;
import com.delsin.BankingService.repository.TransactionRepository;
import com.delsin.BankingService.repository.UserRepository;
import com.delsin.BankingService.security.MyUserDetails;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private MyUserDetails userDetails;
    private User senderUser;
    private Account senderAccount;

    @BeforeEach
    public void setUp() {
        userDetails = mock(MyUserDetails.class);
        senderUser = new User();
        senderAccount = new Account();

        given(userDetails.getUsername()).willReturn("senderLogin");
        given(userRepository.findByLogin("senderLogin")).willReturn(Optional.of(senderUser));
        given(accountRepository.findByUser(senderUser)).willReturn(Optional.of(senderAccount));
    }

    @Test
    public void testMoneyTransferSuccess() {
        setSenderAccountBalance(new BigDecimal("100.0"));
        Account recipientAccount = createAccountWithBalance(new BigDecimal("50.00"));
        given(accountRepository.findById(2L)).willReturn(Optional.of(recipientAccount));

        accountService.moneyTransfer(userDetails, 2L, new BigDecimal("30.00"));

        assertThat(senderAccount.getBalance()).isEqualByComparingTo(new BigDecimal("70.00"));
        assertThat(recipientAccount.getBalance()).isEqualByComparingTo(new BigDecimal("80.00"));
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    public void testMoneyTransferInsufficientFunds() {
        setSenderAccountBalance(new BigDecimal("20.0"));

        assertThatThrownBy(() -> accountService.moneyTransfer(userDetails, 2L, new BigDecimal("30.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Insufficient funds in the account");

        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    public void testMoneyTransferRecipientNotFound() {
        setSenderAccountBalance(new BigDecimal("100.0"));
        given(accountRepository.findById(2L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.moneyTransfer(userDetails, 2L, new BigDecimal("30.00")))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Account with id:2 not found.");

        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    private void setSenderAccountBalance(BigDecimal balance) {
        senderAccount.setBalance(balance);
    }

    private Account createAccountWithBalance(BigDecimal balance) {
        Account account = new Account();
        account.setBalance(balance);
        return account;
    }
}
