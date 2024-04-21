package com.dandev.tictactoeturbo.webSocketConnection.infra.exceptions;

import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

public class WebSocketExceptionAbstract extends RuntimeException{

    private UUID id;
    public WebSocketExceptionAbstract(String message, UUID id) {
        super(message);
        this.id = id;
    }
    public WebSocketExceptionAbstract(String message) {
        super(message);
    }
    public UUID getId(){
        return this.id;
    }
}
