package com.delsin.BankingService.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UserCreateDTO {
    private String login;
    private String fullName;
    private String email;
    private String userPassword;
    private String phone;
    private LocalDate birthday;
    private BigDecimal downPayment;

}
