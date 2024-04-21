package com.dandev.tictactoeturbo.controllers;

import com.dandev.tictactoeturbo.controllers.dtos.UrlDto;
import com.dandev.tictactoeturbo.infra.security.cookies.CookieResolverService;
import com.dandev.tictactoeturbo.model.entity.User;
import com.dandev.tictactoeturbo.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@RestController()
@RequestMapping("/api/public/login/OAuth2")
public class OAuth2Controller {

    @Value("${spring.security.oauth2.resourceserver.opaque-token.clientId}")
    private String clientId;
    @Value("${spring.security.oauth2.resourceserver.opaque-token.clientSecret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.resourceserver.redirect.url}")
    private String redirectUrl;
    @Autowired
    private OpaqueTokenIntrospector introspector;
    @Autowired
    private UserService userService;
    @Autowired
    @Qualifier("cookieResolverOAuth2")
    private CookieResolverService coookieService;


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
    public ResponseEntity<HttpStatus> callback(
            @RequestParam("code") String code,
            HttpServletResponse response) throws IOException {


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


        Optional<User> user = userService.findByEmail(userInfo.getAttribute("email"));

        if (user.isEmpty()) {
            User newUser = new User(null, userInfo.getAttribute("name"), userInfo.getAttribute("email"), null, null);
            user = Optional.of(this.userService.save(newUser));

        }

        response = coookieService.addCookies(response, token, user.get().getId());

        return ResponseEntity.ok().build();

    }


}
