package com.dandev.tictactoeturbo.infra.beansFactory;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientBean {

    @Value("${spring.security.oauth2.resourceserver.opaque-token.introspection-uri}")
    private String introspectURI;

    @Bean
    public WebClient userInfoClient(){

        return WebClient.builder().baseUrl(introspectURI).build();
    }
}
