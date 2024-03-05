package com.delsin.BankingService.service.impl;

import com.delsin.BankingService.model.Email;
import com.delsin.BankingService.model.Phone;
import com.delsin.BankingService.model.User;
import com.delsin.BankingService.repository.EmailRepository;
import com.delsin.BankingService.repository.PhoneRepository;
import com.delsin.BankingService.repository.UserRepository;
import com.delsin.BankingService.security.MyUserDetails;
import com.delsin.BankingService.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;

    @Override
    public void addEmail(MyUserDetails userDetails, String email) {
        User user = currentUser(userDetails);
        if(checkEmailUniq(email)){
            throw new IllegalStateException("This email is already taken: " + email);
        } else {
            user.getEmails().add(new Email(email, user));
            userRepository.save(user);
        }
    }

    @Override
    public void updateEmail(MyUserDetails userDetails, String emailToReplace, String newEmail) {
        User user = currentUser(userDetails);
        if(checkEmailUniq(newEmail)){
            throw new IllegalStateException("This email is already taken: " + newEmail);
        } else {
            List<Email> updatedEmails = user.getEmails().stream()
                    .map(email -> email.getEmail().equals(emailToReplace) ? new Email(newEmail, user) : email)
                    .toList();
            userRepository.save(user);
        }
    }

    @Override
    public void deleteEmail(MyUserDetails userDetails, String email) {
        User user = currentUser(userDetails);
        Email emailToRemove = user.getEmails().stream()
                .filter(e -> e.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Email not found: " + email));
        user.getEmails().remove(emailToRemove);
        userRepository.save(user);
    }

    @Override
    public void addPhone(MyUserDetails userDetails, String phone) {
        User user = currentUser(userDetails);
        if(checkPhoneUniq(phone)){
            throw new IllegalStateException("This phone is already taken: " + phone);
        } else {
            user.getPhones().add(new Phone(phone, user));
            phoneRepository.save(new Phone(phone, currentUser(userDetails)));
        }
    }

    @Override
    public void updatePhone(MyUserDetails userDetails, String phoneToReplace, String newPhone) {
        User user = currentUser(userDetails);
        if(checkPhoneUniq(newPhone)){
            throw new IllegalStateException("This phone is already taken: " + newPhone);
        } else {
            List<Phone> updatedPhone = user.getPhones().stream()
                    .map(email -> email.getPhone().equals(phoneToReplace) ? new Phone(newPhone, user) : email)
                    .toList();
            userRepository.save(user);
        }
    }

    @Override
    public void deletePhone(MyUserDetails userDetails, String phone) {
        User user = currentUser(userDetails);
        Phone phoneToRemove = user.getPhones().stream()
                .filter(p -> p.getPhone().equals(phone))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Phone not found: " + phone));
        user.getEmails().remove(phoneToRemove);
        userRepository.save(user);

    }

    private User currentUser(MyUserDetails userDetails){
        String login = userDetails.getUsername();
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(login + " not exist"));
    }
    //todo проверять перед всеми методами кроме delete
    boolean checkEmailUniq(String email){
        return emailRepository.existsByEmail(email);
    }
    boolean checkPhoneUniq(String phone){
        return phoneRepository.existsByPhone(phone);
    }


}
