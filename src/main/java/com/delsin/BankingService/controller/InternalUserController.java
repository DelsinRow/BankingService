package com.delsin.BankingService.controller;

import com.delsin.BankingService.model.dto.UserCreateDTO;
import com.delsin.BankingService.service.InternalAccountService;
import com.delsin.BankingService.service.impl.InternalAccountServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/internal")
@AllArgsConstructor
public class InternalUserController {
    private final InternalAccountService service;
    @PostMapping("/new-user")
    public String addUser(@RequestBody UserCreateDTO user){
        service.addUser(user);
        return "User is saved";
    }




}
