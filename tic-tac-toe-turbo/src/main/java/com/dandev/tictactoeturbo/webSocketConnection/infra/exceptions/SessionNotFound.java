package com.dandev.tictactoeturbo.webSocketConnection.infra.exceptions;

public class SessionNotFound extends RuntimeException {
    public SessionNotFound(String message) {
        super(message);
    }
}
