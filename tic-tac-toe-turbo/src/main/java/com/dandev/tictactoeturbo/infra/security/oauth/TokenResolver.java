package com.dandev.tictactoeturbo.infra.security.oauth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.stereotype.Component;

@Component
public class TokenResolver implements BearerTokenResolver {
    @Override
    public String resolve(HttpServletRequest request) {
        System.out.println("token resolver");
        if(request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("opaque_token_access")) {
                String value = cookie.getValue();
                if (value.isEmpty() || value.isBlank()) return null;
                System.out.println("cookie-> "+value);
                return value;
            }
        }

        return null;
    }
}
