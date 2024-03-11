package com.delsin.BankingService.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private BigDecimal downPayment;
    private BigDecimal accruedInterest = BigDecimal.valueOf(0);
    private BigDecimal balance;

    private boolean isActiveDepositIncrease = true;
}
