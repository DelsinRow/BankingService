package com.delsin.BankingService.service.impl;

import com.delsin.BankingService.model.UserSpecifications;
import com.delsin.BankingService.model.dto.SearchUserResponse;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public Page<SearchUserResponse> searchUsers(LocalDate birthDate, String phone, String name, String email, Pageable pageable) {
        Specification<User> spec = Specification.where(null);

        if (birthDate != null) {
            spec = spec.and(UserSpecifications.birthDateAfter(birthDate));
        }
        if (phone != null && !phone.isEmpty()) {
            spec = spec.and(UserSpecifications.phoneEquals(phone));
        }
        if (name != null && !name.isEmpty()) {
            spec = spec.and(UserSpecifications.fullNameLike(name));
        }
        if (email != null && !email.isEmpty()) {
            spec = spec.and(UserSpecifications.emailEquals(email));
        }

        return userRepository.findAll(spec, pageable)
                .map(user -> new SearchUserResponse(
                        user.getLogin(),
                        user.getFullName(),
                        user.getEmails().stream().map(Email::getEmail).toList(),
                        user.getPhones().stream().map(Phone::getPhone).toList(),
                        user.getBirthday()
                ));
    }


    @Override
    @Transactional
    public void addEmail(MyUserDetails userDetails, String email) {
        User user = currentUser(userDetails);
        if (isEmailNotUniq(email)) {
            throw new IllegalStateException("This email is already taken: " + email);
        } else {
            user.getEmails().add(new Email(email, user));
            userRepository.save(user);
        }
    }

    @Override
    @Transactional
    public void updateEmail(MyUserDetails userDetails, String emailToReplace, String newEmail) {
        User user = currentUser(userDetails);
        if (isEmailNotUniq(newEmail)) {
            throw new IllegalStateException("This email is already taken: " + newEmail);
        } else {
            List<Email> updatedEmails = user.getEmails().stream()
                    .map(email -> email.getEmail().equals(emailToReplace) ? new Email(newEmail, user) : email)
                    .collect(Collectors.toList());
            user.getEmails().clear();
            user.getEmails().addAll(updatedEmails);

            userRepository.save(user);
        }
    }

    @Override
    @Transactional
    public void deleteEmail(MyUserDetails userDetails, String email) {
        User user = currentUser(userDetails);
        if (user.getEmails().size() == 1) {
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
    @Transactional
    public void addPhone(MyUserDetails userDetails, String phone) {
        User user = currentUser(userDetails);
        if (isPhoneNotUniq(phone)) {
            throw new IllegalStateException("This phone is already taken: " + phone);
        } else {
            user.getPhones().add(new Phone(phone, user));
            phoneRepository.save(new Phone(phone, currentUser(userDetails)));
        }
    }

    @Override
    @Transactional
    public void updatePhone(MyUserDetails userDetails, String phoneToReplace, String newPhone) {
        User user = currentUser(userDetails);
        if (isPhoneNotUniq(newPhone)) {
            throw new IllegalStateException("This phone is already taken: " + newPhone);
        } else {
            List<Phone> updatedPhone = user.getPhones().stream()
                    .map(email -> email.getPhone().equals(phoneToReplace) ? new Phone(newPhone, user) : email)
                    .collect(Collectors.toList());
            user.getPhones().clear();
            user.getPhones().addAll(updatedPhone);

            userRepository.save(user);
        }
    }

    @Override
    @Transactional
    public void deletePhone(MyUserDetails userDetails, String phone) {
        User user = currentUser(userDetails);
        if (user.getPhones().size() == 1) {
            throw new IllegalStateException("You can't delete the last phone.");
        } else {
            Phone phoneToRemove = user.getPhones().stream()
                    .filter(p -> p.getPhone().equals(phone))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("Phone not found: " + phone));
            user.getEmails().remove(phoneToRemove);
            userRepository.save(user);
        }
    }

    private User currentUser(MyUserDetails userDetails) {
        String login = userDetails.getUsername();
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(login + " not exist"));
    }
    private boolean isEmailNotUniq(String email) {
        return emailRepository.existsByEmail(email);
    }
    private boolean isPhoneNotUniq(String phone) {
        return phoneRepository.existsByPhone(phone);
    }

}
