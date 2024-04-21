package com.dandev.tictactoeturbo.controllers;

import com.dandev.tictactoeturbo.controllers.dtos.LoginDto;
import com.dandev.tictactoeturbo.infra.security.jwt.TokenResolverService;
import com.dandev.tictactoeturbo.infra.security.cookies.CookieResolverService;
import com.dandev.tictactoeturbo.model.entity.User;
import com.dandev.tictactoeturbo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/login/basicAuth")
public class AuthBasicController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @Autowired
    private TokenResolverService tokenService;

    @Autowired
    @Qualifier("cookieResolverBasicAuth")
    CookieResolverService cookieService;

    @PostMapping()
    public ResponseEntity<?> login(
            @RequestBody LoginDto userDto,
            HttpServletResponse response) {

        System.out.println(userDto.toString());
        if (!userService.usernameAlreadyExists(userDto.username()))

            throw new RuntimeException("Usuário inexistente ou senha inválida!");

        var usernamePassword = new UsernamePasswordAuthenticationToken(userDto.username(), userDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var user = (User) auth.getPrincipal();
        String token = tokenService.generateToken(user);

        cookieService.addCookies(response, token, user.getId());

        return ResponseEntity.ok().build();
    }
}
