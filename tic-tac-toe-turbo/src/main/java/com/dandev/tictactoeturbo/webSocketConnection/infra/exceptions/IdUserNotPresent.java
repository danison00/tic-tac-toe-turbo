package com.dandev.tictactoeturbo.webSocketConnection.infra.exceptions;

public class IdUserNotPresent extends RuntimeException {
    public IdUserNotPresent(String message) {
        super(message);
    }
}
