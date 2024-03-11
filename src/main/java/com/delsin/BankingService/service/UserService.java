package com.delsin.BankingService.service;

import com.delsin.BankingService.model.dto.SearchUserResponse;
import com.delsin.BankingService.security.MyUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface UserService {
    Page<SearchUserResponse> searchUsers(LocalDate birthDate, String phone, String name, String email, Pageable pageable);

    void addEmail(MyUserDetails userDetails, String email);

    void updateEmail(MyUserDetails userDetails, String emailToReplace, String newEmail);

    void deleteEmail(MyUserDetails userDetails, String email);

    void addPhone(MyUserDetails userDetails, String phone);

    void updatePhone(MyUserDetails userDetails, String phoneToReplace, String newPhone);

    void deletePhone(MyUserDetails userDetails, String phone);


}
