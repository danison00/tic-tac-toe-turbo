package com.dandev.tictactoeturbo.webSocketConnection.infra.config;


import com.dandev.tictactoeturbo.webSocketConnection.infra.handlerExeptionAdvice.WebSocketHandlerExceptionAdvice;
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
    private WebSocketHandlerExceptionAdvice webSocketHandlerExceptionAdvice;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandlerExceptionAdvice, "/game").setAllowedOrigins("*");
    }
}
