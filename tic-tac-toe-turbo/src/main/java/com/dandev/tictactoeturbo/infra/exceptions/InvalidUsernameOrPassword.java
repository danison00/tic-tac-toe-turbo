package com.dandev.tictactoeturbo.infra.exceptions;

public class InvalidUsernameOrPassword extends RuntimeException {
    public InvalidUsernameOrPassword(String message) {
        super(message);
    }
}
