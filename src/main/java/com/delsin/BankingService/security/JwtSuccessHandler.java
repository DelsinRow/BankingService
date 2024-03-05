package com.delsin.BankingService.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Date;

public class JwtSuccessHandler implements AuthenticationSuccessHandler {
    private static final String SECRET_KEY = System.getenv("SECRET_KEY");
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000)) // 10 days
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        response.addHeader("Authorization", "Bearer " + token);
    }
}

