package it.luminari.UniMuiscBackend.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import it.luminari.UniMuiscBackend.exceptions.UnauthorizedException;
import it.luminari.UniMuiscBackend.user.User;
import it.luminari.UniMuiscBackend.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.security.SignatureException;
import java.util.Optional;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JWTAuthFilter.class);


    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                jwtTools.verifyToken(token);

                Long userId = (long) jwtTools.extractIdFromToken(token);
                Optional<User> userOptional = userService.getUserById(userId);

                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new UnauthorizedException("User not found");
                }
            }
        } catch (UnauthorizedException e) {
            logger.error("Unauthorized access: {}", e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
            return;
        } catch (ExpiredJwtException e) {
            logger.error("JWT token expired: {}", e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token expired");
            return;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
            return;
        }

        filterChain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return pathMatcher.match("/auth/**", request.getServletPath());
    }
}