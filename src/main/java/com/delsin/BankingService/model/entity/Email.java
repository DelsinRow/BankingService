package com.delsin.BankingService.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Email(String email, User user) {
        this.email = email;
        this.user = user;
    }
}
