package br.com.finance_project.personal_finance_api.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateUserToken(UserDetails userDetails);
    String generateToken(UserDetails userDetails, long expirationMillis);
    String extractUsername(String token);
    Boolean isTokenValid(String token, UserDetails userDetails);
}
