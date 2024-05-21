package com.dandev.tictactoeturbo.infra.security.config;


import com.dandev.tictactoeturbo.infra.security.jwt.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityFilterConfigBean {

    @Autowired
    private JWTAuthenticationFilter jwtAuthentication;

    @Bean
    public SecurityFilterChain securityfilter(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {
//                    authorize.anyRequest().permitAll();
                    authorize.requestMatchers("/", "/health", "tic-tac-toe/tic-tac-toe/**", "api/public/**", "/templates/**", "/js/**", "/css/**", "/assets/**").permitAll()
                            .anyRequest().authenticated();

                })
                .oauth2ResourceServer(c -> {
                    c.opaqueToken(Customizer.withDefaults());
                })
                .addFilterBefore(jwtAuthentication, BearerTokenAuthenticationFilter.class)
                .build();

    }

}
