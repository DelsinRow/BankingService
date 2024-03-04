package com.delsin.BankingService.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private BigDecimal balance;     //not null


}
