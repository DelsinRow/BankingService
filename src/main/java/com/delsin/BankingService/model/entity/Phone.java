package com.delsin.BankingService.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Phone(String phone, User user) {
        this.phone = phone;
        this.user = user;
    }
}
