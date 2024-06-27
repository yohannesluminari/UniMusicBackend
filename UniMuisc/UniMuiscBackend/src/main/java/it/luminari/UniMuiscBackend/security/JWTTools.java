package it.luminari.UniMuiscBackend.security;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import it.luminari.UniMuiscBackend.exceptions.UnauthorizedException;
import it.luminari.UniMuiscBackend.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTTools {

    @Value("${spring.application.jwt.secret}")
    private String secret; // This should be removed as it's not secure

    @Value("${spring.application.jwt.expiration-ms}")
    private long duration;

    // Use a secure key instead of String secret
    private final SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

    // JWT token creation
    public String createToken(User user) {
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + duration))
                .setSubject(String.valueOf(user.getId()))
                .signWith(key)
                .compact();
    }


    // JWT Token validation

    public void verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).
                    build().parse(token);
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException("Token is not valid.");
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("Token no longer valid, date has expired");
        } catch (Exception e) {
            throw new UnauthorizedException("Ongoing issues with token, please try to login again.");
        }
    }

    // Extract ID from JWT Token

    public int extractIdFromToken(String token) {
        return Integer.parseInt(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).
                build().parseSignedClaims(token).getPayload().getSubject());
    }
}