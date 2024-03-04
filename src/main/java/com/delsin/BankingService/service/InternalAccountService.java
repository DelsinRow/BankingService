package com.delsin.BankingService.service;

import com.delsin.BankingService.model.User;
import com.delsin.BankingService.model.dto.UserCreateDTO;

public interface InternalAccountService {
    void addUser(UserCreateDTO user);
}
