package com.dandev.tictactoeturbo.infra.exceptions;

public class UsernameUnavailable extends RuntimeException {
    public UsernameUnavailable(String message) {
        super(message);
    }
}
