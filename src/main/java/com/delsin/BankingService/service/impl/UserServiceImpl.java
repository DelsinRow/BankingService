package com.delsin.BankingService.service.impl;

import com.delsin.BankingService.repository.EmailRepository;
import com.delsin.BankingService.repository.PhoneRepository;
import com.delsin.BankingService.repository.UserRepository;
import com.delsin.BankingService.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;

    @Override
    public void addEmail(String email) {


    }

    @Override
    public void updateEmail(String email) {

    }

    @Override
    public void deleteEmail(String email) {

    }

    @Override
    public void addPhone(String phone) {

    }

    @Override
    public void updatePhone(String phone) {

    }

    @Override
    public void deletePhone(String phone) {

    }
}
