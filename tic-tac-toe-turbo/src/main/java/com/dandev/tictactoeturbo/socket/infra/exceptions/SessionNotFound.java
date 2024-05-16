package com.dandev.tictactoeturbo.socket.infra.exceptions;

public class SessionNotFound extends RuntimeException {
    public SessionNotFound(String message) {
        super(message);
    }
}
