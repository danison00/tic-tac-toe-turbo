package com.dandev.tictactoeturbo.socket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@EnableWebSocket
@Configuration
@CrossOrigin("*")
public class WebSocketConfigImpl implements WebSocketConfigurer {

    @Autowired
    private com.dandev.tictactoeturbo.socket.config.TextWebSocketHandler TextWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(TextWebSocketHandler, "/socket/connect").setAllowedOrigins("*");
    }
}
