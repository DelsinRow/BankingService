package com.delsin.BankingService.service.impl;

import com.delsin.BankingService.model.Account;
import com.delsin.BankingService.model.Email;
import com.delsin.BankingService.model.Phone;
import com.delsin.BankingService.model.User;
import com.delsin.BankingService.model.dto.UserCreateDTO;
import com.delsin.BankingService.repository.AccountRepository;
import com.delsin.BankingService.repository.EmailRepository;
import com.delsin.BankingService.repository.PhoneRepository;
import com.delsin.BankingService.repository.UserRepository;
import com.delsin.BankingService.service.InternalAccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InternalAccountServiceImpl implements InternalAccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    //todo @Transactional
    public void addUser(UserCreateDTO user) {
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

            Account account = Account.builder()
                    .user(newUser)
                    .balance(user.getBalance())
                    .build();

            userRepository.save(newUser);
            accountRepository.save(account);
            emailRepository.save(new Email(user.getEmail(), newUser));
            phoneRepository.save(new Phone(user.getPhone(), newUser));
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
