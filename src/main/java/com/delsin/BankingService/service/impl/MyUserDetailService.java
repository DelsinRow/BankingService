package com.delsin.BankingService.service.impl;

import com.delsin.BankingService.config.MyUserDetails;
import com.delsin.BankingService.model.User;
import com.delsin.BankingService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(login);
        return user.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(login + " not found"));
    }
}
