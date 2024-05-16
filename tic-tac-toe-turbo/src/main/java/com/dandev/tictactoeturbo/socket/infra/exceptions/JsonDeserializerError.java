package com.dandev.tictactoeturbo.socket.infra.exceptions;

public class JsonDeserializerError extends RuntimeException {
    public JsonDeserializerError(String obj, Class<?> clazz, Throwable e) {
        super("Error when deserializer " + obj + " to " + clazz, e);
    }

    public JsonDeserializerError(String value, String typeReference, Throwable e) {
        super("Error when deserializer " + value + " to " + typeReference, e);
    }
}
