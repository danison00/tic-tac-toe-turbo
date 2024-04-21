package com.dandev.tictactoeturbo.infra.security.jwt;

import com.dandev.tictactoeturbo.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenResolverService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.recoveryToken(request);
        if (token != null) {
            String username = tokenService.validateToken(token);
            if (username != null) {
                UserDetails user = userService.loadUserByUsername(username);
                if (user != null) {
                    var userAuthentication = new UsernamePasswordAuthenticationToken(username, user, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(userAuthentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {

        var cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("JWT_TOKEN")) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
