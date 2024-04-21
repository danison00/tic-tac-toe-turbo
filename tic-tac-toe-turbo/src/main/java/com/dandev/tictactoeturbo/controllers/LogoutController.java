package com.dandev.tictactoeturbo.controllers;

import com.dandev.tictactoeturbo.infra.security.cookies.CookieResolverService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/public/logout")
@RestController
public class LogoutController {

    @Autowired
    @Qualifier("cookieResolverBasicAuth")
    private CookieResolverService cookieService;


    @GetMapping
    public ResponseEntity<HttpStatus> logout(HttpServletResponse response, HttpServletRequest request) {
        cookieService.removeCookies(request, response);
        return ResponseEntity.ok().build();
    }

}
