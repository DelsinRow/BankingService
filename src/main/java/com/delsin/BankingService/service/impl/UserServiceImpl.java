package com.delsin.BankingService.service.impl;

import com.delsin.BankingService.model.entity.Email;
import com.delsin.BankingService.model.entity.Phone;
import com.delsin.BankingService.model.entity.User;
import com.delsin.BankingService.repository.EmailRepository;
import com.delsin.BankingService.repository.PhoneRepository;
import com.delsin.BankingService.repository.UserRepository;
import com.delsin.BankingService.security.MyUserDetails;
import com.delsin.BankingService.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;

    @Override
    public void addEmail(MyUserDetails userDetails, String email) {
        User user = currentUser(userDetails);
        if(isEmailUniq(email)){
            throw new IllegalStateException("This email is already taken: " + email);
        } else {
            user.getEmails().add(new Email(email, user));
            userRepository.save(user);
        }
    }

    @Override
    public void updateEmail(MyUserDetails userDetails, String emailToReplace, String newEmail) {
        User user = currentUser(userDetails);
        if(isEmailUniq(newEmail)){
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
        if (user.getEmails().size() == 1 ){
            throw new IllegalStateException("You can't delete the last email.");
        } else {
            Email emailToRemove = user.getEmails().stream()
                    .filter(e -> e.getEmail().equals(email))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("Email not found: " + email));
            user.getEmails().remove(emailToRemove);
            userRepository.save(user);
        }
    }

    @Override
    public void addPhone(MyUserDetails userDetails, String phone) {
        User user = currentUser(userDetails);
        if(isPhoneUniq(phone)){
            throw new IllegalStateException("This phone is already taken: " + phone);
        } else {
            user.getPhones().add(new Phone(phone, user));
            phoneRepository.save(new Phone(phone, currentUser(userDetails)));
        }
    }

    @Override
    public void updatePhone(MyUserDetails userDetails, String phoneToReplace, String newPhone) {
        User user = currentUser(userDetails);
        if(isPhoneUniq(newPhone)){
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
        if (user.getPhones().size() == 1 ){
            throw new IllegalStateException("You can't delete the last email.");
        } else {
            Phone phoneToRemove = user.getPhones().stream()
                    .filter(p -> p.getPhone().equals(phone))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("Phone not found: " + phone));
            user.getEmails().remove(phoneToRemove);
            userRepository.save(user);
        }
    }

    private User currentUser(MyUserDetails userDetails){
        String login = userDetails.getUsername();
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(login + " not exist"));
    }
    //todo проверять перед всеми методами кроме delete
    private boolean isEmailUniq(String email){
        return emailRepository.existsByEmail(email);
    }
    private boolean isPhoneUniq(String phone){
        return phoneRepository.existsByPhone(phone);
    }

}
