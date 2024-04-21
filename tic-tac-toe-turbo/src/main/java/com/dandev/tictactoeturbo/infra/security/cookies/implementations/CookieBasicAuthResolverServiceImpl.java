package com.dandev.tictactoeturbo.infra.security.cookies.implementations;


import com.dandev.tictactoeturbo.infra.security.cookies.CookieResolverService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service("cookieResolverBasicAuth")
public class CookieBasicAuthResolverServiceImpl implements CookieResolverService {


    @Override
    public HttpServletResponse addCookies(HttpServletResponse response, String token, UUID id) {

        Cookie cookie1 = new Cookie("JWT_Token", token);
        cookie1.setMaxAge(60 * 60);
        cookie1.setHttpOnly(true);
        cookie1.setPath("/");
        response.addCookie(cookie1);

        Cookie cookie2 = new Cookie("is_auth", "true");
        cookie2.setMaxAge(60 * 60);
        cookie2.setPath("/");
        response.addCookie(cookie2);

        Cookie cookie3 = new Cookie("user_id", id.toString());
        cookie3.setMaxAge(60 * 60);
        cookie3.setPath("/");
        response.addCookie(cookie3);
        return response;
    }

    @Override
    public void removeCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieResolverService.super.removeCookies(request, response);
    }
}
