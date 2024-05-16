package com.dandev.tictactoeturbo.infra.exceptions;

public class UriParamsError extends RuntimeException {
    public UriParamsError(String s) {
        super(s);
    }
}
