package com.dandev.tictactoeturbo.socket.infra.reflection.exceptions;

public class ExceptionHandlerNotFound extends RuntimeException {
    public ExceptionHandlerNotFound(Throwable e) {
        super(e);
    }
}
