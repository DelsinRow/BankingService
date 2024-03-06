package com.delsin.BankingService.model.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UserCreateDTO {
    @NotNull(message = "Login cannot be null")
    @Size(min = 1)
    private String login;

    @NotNull(message = "Full name cannot be null")
    @Size(min = 1)
    private String fullName;

    @NotNull(message = "Email cannot be null")
    @Size(min = 1)
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 1)
    private String userPassword;

    @NotNull(message = "Phone cannot be null")
    @Size(min = 7)
    private String phone;

    @NotNull
    @Past(message = "The date of birth must be in the past")
    private LocalDate birthday;

    @NotNull(message = "Down payment cannot be 0")
    @DecimalMin(value = "0.01", message = "The down payment must be greater than 0")
    private BigDecimal downPayment;

}
