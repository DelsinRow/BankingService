package com.delsin.BankingService.service;

import com.delsin.BankingService.security.MyUserDetails;

public interface UserService {
    void addEmail(MyUserDetails userDetails, String email);
    void updateEmail(MyUserDetails userDetails, String emailToReplace, String newEmail);
    void deleteEmail(MyUserDetails userDetails, String email);

    void addPhone(MyUserDetails userDetails, String phone);
    void updatePhone(MyUserDetails userDetails, String phoneToReplace, String newPhone);
    void deletePhone(MyUserDetails userDetails, String phone);




}
