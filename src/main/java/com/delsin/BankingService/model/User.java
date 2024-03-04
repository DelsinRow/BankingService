package com.delsin.BankingService.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String fullName;

    // Используем @OneToMany для связи с Email и Phone
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Email> emails = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phones = new ArrayList<>();

    private String userPassword;
    private LocalDate birthday;



}
