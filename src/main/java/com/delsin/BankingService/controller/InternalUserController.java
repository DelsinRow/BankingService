package com.delsin.BankingService.controller;

import com.delsin.BankingService.auth.AuthenticationResponse;
import com.delsin.BankingService.model.dto.UserCreateDTO;
import com.delsin.BankingService.service.InternalAccountService;
import com.delsin.BankingService.service.impl.AccountServiceImpl;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/internal")
@AllArgsConstructor
public class InternalUserController {
    private final InternalAccountService service;
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @PostMapping("/add-user")
    public String addUser(@Valid @RequestBody UserCreateDTO user) {
        logger.info("Processing POST-request to /api/v1/internal/add-user");
        AuthenticationResponse authenticationResponse = service.addUser(user);
        logger.info("User:" + user.getLogin() + " has token: " + authenticationResponse.getToken());
        return "User is saved";
    }

}
