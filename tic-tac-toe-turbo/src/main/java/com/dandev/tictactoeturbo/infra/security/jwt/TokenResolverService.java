package com.dandev.tictactoeturbo.infra.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dandev.tictactoeturbo.model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenResolverService {

    @Value("${spring.security.authorization.jwt.secret}")
    String secrety;

    @Value("${spring.security.authorization.jwt.expired}")
    private int expiredAuthentication;

    public String generateToken(User user) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secrety);

            return JWT.create().withIssuer("tic-tac-toe-turbo")
                    .withSubject(user.getUsername())
                    .withExpiresAt(LocalDateTime.now().plusSeconds(expiredAuthentication).toInstant(ZoneOffset.of("-03:00")))
                    .sign(algorithm);


        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar token");
        }
    }

    public String validateToken(String token) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secrety);

            return JWT.require(algorithm)
                    .withIssuer("tic-tac-toe-turbo")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }

    }
}
