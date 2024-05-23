package com.delsin.BankingService.controller;

import com.delsin.BankingService.auth.AuthenticationRequest;
import com.delsin.BankingService.auth.AuthenticationResponse;
import com.delsin.BankingService.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        logger.info("Processing POST-request to /api/v1/auth");
        return ResponseEntity.ok(service.authenticate(request));
    }
}
