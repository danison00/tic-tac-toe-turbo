package com.dandev.tictactoeturbo.infra.security.cookies;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface CookieResolverService {

    public HttpServletResponse addCookies(HttpServletResponse response, String token, UUID id);

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
}
