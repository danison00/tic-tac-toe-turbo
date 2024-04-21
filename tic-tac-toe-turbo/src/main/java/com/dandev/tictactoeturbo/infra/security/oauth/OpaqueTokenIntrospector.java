package com.dandev.tictactoeturbo.infra.security.oauth;

import com.dandev.tictactoeturbo.controllers.dtos.UserInfo;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.HashMap;
import java.util.Map;



@Component
public class OpaqueTokenIntrospector implements org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector {

    private final WebClient webClient;

    public OpaqueTokenIntrospector(WebClient webClient){
        this.webClient = webClient;
    }

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {

        System.out.println("introspect active");


        UserInfo userInfo = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth2/v3/userinfo")
                        .queryParam("access_token", token).build())
                .retrieve().bodyToMono(UserInfo.class).block();

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", userInfo.sub());
        attributes.put("name", userInfo.name());
        attributes.put("email", userInfo.email());
        return new OAuth2IntrospectionAuthenticatedPrincipal(userInfo.name(), attributes, null);
    }
}
