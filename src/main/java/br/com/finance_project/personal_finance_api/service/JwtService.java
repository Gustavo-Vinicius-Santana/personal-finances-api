package br.com.finance_project.personal_finance_api.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateUserToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    String generateToken(UserDetails userDetails, long expirationMillis, String type);
    String extractUsername(String token);
    Boolean isTokenValid(String token, UserDetails userDetails);
    String extractTokenType(String token);
}
