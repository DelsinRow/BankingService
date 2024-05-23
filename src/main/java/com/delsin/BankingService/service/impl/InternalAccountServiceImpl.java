package com.delsin.BankingService.service.impl;

import com.delsin.BankingService.auth.AuthenticationResponse;
import com.delsin.BankingService.auth.jwt.JwtService;
import com.delsin.BankingService.model.dto.UserCreateDTO;
import com.delsin.BankingService.model.entity.Account;
import com.delsin.BankingService.model.entity.Email;
import com.delsin.BankingService.model.entity.Phone;
import com.delsin.BankingService.model.entity.User;
import com.delsin.BankingService.repository.AccountRepository;
import com.delsin.BankingService.repository.EmailRepository;
import com.delsin.BankingService.repository.PhoneRepository;
import com.delsin.BankingService.repository.UserRepository;
import com.delsin.BankingService.security.MyUserDetails;
import com.delsin.BankingService.service.InternalAccountService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class InternalAccountServiceImpl implements InternalAccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    @Transactional
    public AuthenticationResponse addUser(UserCreateDTO user) {
        if(checkLoginUniq(user.getLogin())){
            throw new IllegalStateException("This login is already taken.");
        } else if (checkEmailUniq(user.getEmail())) {
            throw new IllegalStateException("This email address is already taken.");
        } else if(checkPhoneUniq(user.getPhone())) {
            throw new IllegalStateException("This phone number is already taken.");
        } else {
            User newUser = new User();
            Email newEmail = new Email(user.getEmail(), newUser);
            Phone newPhone = new Phone(user.getPhone(), newUser);

            newUser.setLogin(user.getLogin());
            newUser.setFullName(user.getFullName());
            newUser.getEmails().add(newEmail);
            newUser.getPhones().add(newPhone);
            newUser.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            newUser.setBirthday(user.getBirthday());
            logger.info("New user with login:" + newUser.getLogin() + " created");

            Account newAccount = new Account();
            newAccount.setDownPayment(user.getDownPayment());
            newAccount.setUser(newUser);
            newAccount.setBalance(user.getDownPayment());
            logger.info("New bank account for user:" + newUser.getLogin() + " created");

            userRepository.save(newUser);
            accountRepository.save(newAccount);

            UserDetails userDetails = new MyUserDetails(newUser);
            var jwtToken = jwtService.generateToken(userDetails);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
    }

    boolean checkLoginUniq(String login) {
        return userRepository.existsByLogin(login);
    }
    boolean checkEmailUniq(String email){
        return emailRepository.existsByEmail(email);
    }
    boolean checkPhoneUniq(String phone){
        return phoneRepository.existsByPhone(phone);
    }

}
