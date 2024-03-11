package com.delsin.BankingService.service;

import com.delsin.BankingService.auth.AuthenticationResponse;
import com.delsin.BankingService.model.dto.UserCreateDTO;

public interface InternalAccountService {
    AuthenticationResponse addUser(UserCreateDTO user);
}
