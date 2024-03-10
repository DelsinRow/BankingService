package com.delsin.BankingService.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @NotNull(message = "Down payment cannot be 0")
    @DecimalMin(value = "0.01", message = "The down payment must be greater than 0")
    private BigDecimal downPayment;
    private BigDecimal accruedInterest = BigDecimal.valueOf(0);
    private BigDecimal balance;

    private boolean isActiveDepositIncrease = true;
}
