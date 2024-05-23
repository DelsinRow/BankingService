package com.delsin.BankingService.controller;

import com.delsin.BankingService.security.MyUserDetails;
import com.delsin.BankingService.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @PostMapping("/email")
    public ResponseEntity<?> addEmail(@AuthenticationPrincipal MyUserDetails userDetails,
                                      @RequestParam String email) {
        logger.info("Processing POST-request to /api/v1/users/email");
        try {
            userService.addEmail(userDetails, email);
            return ResponseEntity.ok().body("Email added successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/email")
    public ResponseEntity<?> updateEmail(@AuthenticationPrincipal MyUserDetails userDetails,
                                         @RequestParam String emailToReplace,
                                         @RequestParam String newEmail) {
        logger.info("Processing PATCH-request to /api/v1/users/email");
        try {
            userService.updateEmail(userDetails, emailToReplace, newEmail);
            return ResponseEntity.ok().body("Email updated successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/email")
    public ResponseEntity<?> deleteEmail(@AuthenticationPrincipal MyUserDetails userDetails,
                                         @RequestParam String email) {
        logger.info("Processing DELETE-request to /api/v1/users/email");
        try {
            userService.deleteEmail(userDetails, email);
            return ResponseEntity.ok().body("Email deleted successfully.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/phone")
    public ResponseEntity<?> addPhone(@AuthenticationPrincipal MyUserDetails userDetails,
                                      @RequestParam String phone) {
        logger.info("Processing POST-request to /api/v1/users/phone");
        try {
            userService.addPhone(userDetails, phone);
            return ResponseEntity.ok().body("Phone added successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/phone")
    public ResponseEntity<?> updatePhone(@AuthenticationPrincipal MyUserDetails userDetails,
                                         @RequestParam String phoneToReplace,
                                         @RequestParam String newPhone) {
        logger.info("Processing PUT-request to /api/v1/users/phone");
        try {
            userService.updatePhone(userDetails, phoneToReplace, newPhone);
            return ResponseEntity.ok().body("Phone updated successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/phone")
    public ResponseEntity<?> deletePhone(@AuthenticationPrincipal MyUserDetails userDetails,
                                         @RequestParam String phone) {
        logger.info("Processing DELETE-request to /api/v1/users/phone");
        try {
            userService.deletePhone(userDetails, phone);
            return ResponseEntity.ok().body("Phone deleted successfully.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
