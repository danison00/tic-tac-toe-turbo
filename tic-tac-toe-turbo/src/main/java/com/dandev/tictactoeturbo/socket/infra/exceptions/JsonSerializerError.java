package com.dandev.tictactoeturbo.socket.infra.exceptions;

public class JsonSerializerError extends RuntimeException {
    public JsonSerializerError(Object obj, Throwable e) {
        super("Error when serializer "+ obj,e);
    }
}
