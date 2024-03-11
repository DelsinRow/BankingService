package com.delsin.BankingService.controller;

import com.delsin.BankingService.security.MyUserDetails;
import com.delsin.BankingService.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @PostMapping("/money-transfer")
    public ResponseEntity<?> transferMoney(@AuthenticationPrincipal MyUserDetails userDetails,
                                           @RequestParam Long recipientId,
                                           @RequestParam BigDecimal amount) {
        logger.info("Processing POST-request to /api/v1/accounts/money-transfer");
        try {
            accountService.moneyTransfer(userDetails, recipientId, amount);
            return ResponseEntity.ok().body("Transfer successful");
        } catch (EntityNotFoundException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
