package br.com.finance_project.personal_finance_api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceIml implements JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }

    @Override
    public String generateUserToken(UserDetails userDetails) {
        return generateToken(userDetails, jwtExpiration, "access");
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails, refreshExpiration, "refresh");
    }

    public String generateToken(UserDetails userDetails, long expirationMillis, String type) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + expirationMillis);

        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withClaim("type", type)
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

    public String extractTokenType(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(getAlgorithm())
                    .build()
                    .verify(token);

            return decodedJWT.getClaim("type").asString();
        } catch (JWTVerificationException e) {
            return null;
        }
    }
}
