package com.dandev.tictactoeturbo.infra.security.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class RequestMatcherAuthBasic implements RequestMatcher {
    @Override
    public boolean matches(HttpServletRequest request) {
        String authorizationType = request.getHeader("AuthorizationType");
        if(authorizationType == null) return false;
        return  authorizationType.equalsIgnoreCase("basic_auth");
    }
}
