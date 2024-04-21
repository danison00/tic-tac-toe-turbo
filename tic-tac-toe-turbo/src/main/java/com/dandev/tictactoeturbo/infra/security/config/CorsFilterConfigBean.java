package com.dandev.tictactoeturbo.infra.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsFilterConfigBean {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Permite o envio de cookies
        config.addAllowedOrigin("http://d33a2ylw1wwl03.cloudfront.net/"); // Domínio permitido
        config.addAllowedOrigin("http://localhost:4200/");
        config.addAllowedOrigin("http://ec2-3-12-196-39.us-east-2.compute.amazonaws.com:8080");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


}
