package com.dandev.tictactoeturbo.controllers;

import com.dandev.tictactoeturbo.controllers.dtos.LoginDto;
import com.dandev.tictactoeturbo.controllers.dtos.LoginResponseDto;
import com.dandev.tictactoeturbo.controllers.dtos.UrlDto;
import com.dandev.tictactoeturbo.controllers.dtos.UsernameExistsDto;
import com.dandev.tictactoeturbo.infra.security.jwt.TokenService;
import com.dandev.tictactoeturbo.model.entity.User;
import com.dandev.tictactoeturbo.repository.UserRespository;
import com.dandev.tictactoeturbo.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@RestController()
@RequestMapping("/api/public/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${spring.security.oauth2.resourceserver.opaque-token.clientId}")
    private String clientId;
    @Value("${spring.security.oauth2.resourceserver.opaque-token.clientSecret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.resourceserver.redirect.url}")
    private String redirectUrl;
    @Value("${spring.security.authentication.cookie.domain}")
    private String cookieDomain;
    @Autowired
    private UserRespository userRespository;
    @Autowired
    private OpaqueTokenIntrospector introspector;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/url")
    public ResponseEntity<UrlDto> auth() {
        String url = new GoogleAuthorizationCodeRequestUrl(clientId,
                redirectUrl,
                Arrays.asList(
                        "email",
                        "profile",
                        "openid")).build();

        System.out.println(url);

        return ResponseEntity.ok(new UrlDto(url));

    }

    @GetMapping("/callback")
    public ResponseEntity<HttpStatus> callback(@RequestParam("code") String code, HttpServletResponse response, HttpServletRequest request) throws IOException {
        System.out.println(request.getCookies() + "nfbshjfbhsdfbhsfbdsf");

        String token;
        try {
            token = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    new GsonFactory(),
                    clientId,
                    clientSecret,
                    code,
                    redirectUrl).execute().getAccessToken();

        } catch (IOException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var userInfo = introspector.introspect(token);


        Optional<User> user = userRespository.findByEmail(userInfo.getAttribute("email"));

        if (user.isEmpty()) {
            User newUser = new User(null, userInfo.getAttribute("name"), userInfo.getAttribute("email"), null, null);
            user = Optional.of(this.userService.save(newUser));

        }


        Cookie cookie = new Cookie("opaque_token_access", token);
        cookie.setMaxAge(60 * 60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        Cookie cookie1 = new Cookie("is_auth", "true");
        cookie1.setMaxAge(60 * 60);
        cookie1.setPath("/");
        response.addCookie(cookie1);

        Cookie cookie2 = new Cookie("user_id", user.get().getId().toString());
        cookie2.setMaxAge(60 * 60);
        cookie2.setPath("/");
        response.addCookie(cookie2);

        return ResponseEntity.ok().build();

    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto user, HttpServletResponse response) {

        System.out.println(user.toString());
        if (!userService.usernameAlreadyExists(user.username()))

            throw new RuntimeException("Usuário inexistente ou senha inválida!");

        var usernamePassword = new UsernamePasswordAuthenticationToken(user.username(), user.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((User) auth.getPrincipal());

        Cookie cookie = new Cookie("JWT_Token", token);
        cookie.setMaxAge(60 * 60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        Cookie cookie1 = new Cookie("is_auth", "true");
        cookie1.setMaxAge(60 * 60);
        cookie1.setPath("/");
        response.addCookie(cookie1);

        Cookie cookie2 = new Cookie("user_id", ((User) auth.getPrincipal()).getId().toString());
        cookie2.setMaxAge(60 * 60);
        cookie2.setPath("/");
        response.addCookie(cookie2);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/logout")
    public ResponseEntity<HttpStatus> logout(HttpServletResponse response, HttpServletRequest request) {

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
        Cookie c = new Cookie("opaque_token_access", null);
        c.setPath("/");
        c.setMaxAge(0);
        response.addCookie(c);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/username-exists")
    public ResponseEntity<UsernameExistsDto> usernameExists(@RequestParam("username") String username) {

        return ResponseEntity.ok(new UsernameExistsDto(this.userService.usernameAlreadyExists(username)));
    }
}
