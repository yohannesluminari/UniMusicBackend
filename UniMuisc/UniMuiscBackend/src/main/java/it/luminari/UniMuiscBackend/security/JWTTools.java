package it.luminari.UniMuiscBackend.security;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import it.luminari.UniMuiscBackend.exceptions.UnauthorizedException;
import it.luminari.UniMuiscBackend.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {

    @Value("${spring.application.jwt.secret}")
    private String secret;

    @Value("${spring.application.jwt.expiration-ms}")
    private long duration;

    // JWT token creation

    public String createToken(User user) {
        return Jwts.builder().issuedAt(new Date(System.currentTimeMillis())).
                expiration(new Date(System.currentTimeMillis() + duration)).
                subject(String.valueOf(user.getId())).
                signWith(Keys.hmacShaKeyFor(secret.getBytes())).
                compact();
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