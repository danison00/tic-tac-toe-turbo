package com.dandev.tictactoeturbo.socket.infra.exceptions;

public class IdUserNotPresent extends RuntimeException {
    public IdUserNotPresent(String message) {
        super(message);
    }
}
