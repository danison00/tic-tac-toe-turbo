package com.dandev.tictactoeturbo.socket.infra.exceptions;

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
