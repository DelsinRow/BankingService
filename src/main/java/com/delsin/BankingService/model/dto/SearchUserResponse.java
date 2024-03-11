package com.delsin.BankingService.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class SearchUserResponse {
    private String login;
    private String fullName;
    private List<String> email;
    private List<String> phone;
    private LocalDate birthday;

}
