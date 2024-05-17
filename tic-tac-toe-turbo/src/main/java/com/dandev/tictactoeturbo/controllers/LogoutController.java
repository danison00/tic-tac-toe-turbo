package com.dandev.tictactoeturbo.controllers;

import com.dandev.tictactoeturbo.infra.security.cookies.CookieResolverService;
import com.dandev.tictactoeturbo.socket.shared.UserOnlineManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;
import java.util.UUID;

@RequestMapping("/api/public/logout")
@RestController
public class LogoutController {

    @Autowired
    @Qualifier("cookieResolverBasicAuth")
    private CookieResolverService cookieService;
    @Autowired
    @Qualifier("webClientLogout")
    private WebClient webClient;

    @Autowired
    private UserOnlineManager userOnlineManager;


    @GetMapping
    public ResponseEntity<HttpStatus> logout(HttpServletResponse response, HttpServletRequest request) {

            Optional<String> tokenOpt = cookieService.getValue(request, "opaque_token_access");
            if (tokenOpt.isPresent()) {
                String token = tokenOpt.get();
                webClient.get().uri(uriBuilder -> uriBuilder.path("o/oauth2/revoke").queryParam("token", token).build()).retrieve().toBodilessEntity().block();
            }

            Optional<UUID> uuidOpt = cookieService.getId(request);
            cookieService.removeCookies(request, response);
            uuidOpt.ifPresent((id)-> userOnlineManager.remove(id));

        return ResponseEntity.ok().build();
    }

}
