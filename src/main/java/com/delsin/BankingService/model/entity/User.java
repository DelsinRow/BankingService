package com.delsin.BankingService.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
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

    @NotNull(message = "Login cannot be null")
    @Size(min = 1)
    private String login;

    @NotNull(message = "Full name cannot be null")
    @Size(min = 1)
    private String fullName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull(message = "Email cannot be null")
    @Size(min = 1)
    private List<Email> emails = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull(message = "Phone cannot be null")
    @Size(min = 7)
    private List<Phone> phones = new ArrayList<>();

    @NotNull(message = "Password cannot be null")
    @Size(min = 1)
    private String userPassword;

    @NotNull
    @Past(message = "The date of birth must be in the past")
    private LocalDate birthday;



}
