package com.delsin.BankingService.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
//@Builder
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private final BigDecimal downPayment;
    private BigDecimal accruedInterest = BigDecimal.valueOf(0);
    private BigDecimal balance;

}
