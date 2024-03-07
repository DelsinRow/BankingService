package com.delsin.BankingService.auth;


import com.delsin.BankingService.auth.jwt.JwtService;
import com.delsin.BankingService.repository.UserRepository;
import com.delsin.BankingService.model.entity.User;
import com.delsin.BankingService.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


//    public AuthenticationResponse register(RegisterRequest request) {
////        var user = User.builder()
////                .email(request.getEmail())
////                .password(passwordEncoder.encode(request.getPassword()))
////                .build();
//        var jwtToken = jwtService.generateToken(userDetails); //todo проверить userDetails
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        var user = repository.findByLogin(request.getLogin())
                .orElseThrow();
        UserDetails userDetails = new MyUserDetails(user);
        var jwtToken = jwtService.generateToken(userDetails);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}