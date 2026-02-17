package br.com.finance_project.personal_finance_api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceIml implements JwtService {
    private String secretKey = "umaChaveSecretaBemSeguraAqui123!";

    private long jwtExpiration = 3600000;

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }

    @Override
    public String generateUserToken(UserDetails userDetails) {
        return generateToken(userDetails, jwtExpiration);
    }

    @Override
    public String generateToken(UserDetails userDetails, long expirationMillis) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + expirationMillis);

        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .sign(getAlgorithm());
    }

    @Override
    public String extractUsername(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(getAlgorithm())
                    .build()
                    .verify(token);
            return decodedJWT.getSubject();
        }
        catch (JWTVerificationException e) {
            return null;
        }
    }

    @Override
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            return username != null && username.equals(userDetails.getUsername());
        }
        catch (JWTVerificationException e) {
            return false;
        }
    }
}
