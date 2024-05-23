package com.delsin.BankingService.controller;

import com.delsin.BankingService.model.dto.SearchUserResponse;
import com.delsin.BankingService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchApiController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @GetMapping("/users")
    public ResponseEntity<Page<SearchUserResponse>> searchUsers(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            Pageable pageable) {
        logger.info("Processing GET-request to /api/v1/search/users");
        Page<SearchUserResponse> users = userService.searchUsers(birthDate, phone, name, email, pageable);
        return ResponseEntity.ok(users);
    }
}
