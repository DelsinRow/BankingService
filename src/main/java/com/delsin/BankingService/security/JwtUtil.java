package com.delsin.BankingService.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {
    private static final String SECRET_KEY = System.getenv("SECRET_KEY");

    public Claims extractClaims(String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return jwsClaims.getBody();
    }

    public String extractUsername(String token) {
        Claims claims = extractClaims(token);
        return claims.get("login"); // Или claims.get("username"), если вы используете custom claim для username
    }
}
