package com.dandev.tictactoeturbo.infra.security.cookies;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;
import java.util.UUID;

public interface CookieResolverService {

    public HttpServletResponse addCookies(HttpServletResponse response, String token, UUID id);

    default Optional<UUID> getId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return Optional.empty();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("user_id")) {
                String id = cookie.getValue();
                if (id != null) return Optional.of(UUID.fromString(id));
            }
        }
        return Optional.empty();
    }

    default void removeCookies(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                Cookie a = new Cookie(c.getName(), null);
                a.setMaxAge(0);
                a.setPath("/");
                a.setHttpOnly(false);
                response.addCookie(a);
                System.out.println(a.getName());

            }
        }
    }

    default Optional<String> getValue(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null)
            for (Cookie c : request.getCookies())
                if (c.getName().equals(cookieName)) return Optional.of(c.getValue());

        return Optional.empty();

    }
}