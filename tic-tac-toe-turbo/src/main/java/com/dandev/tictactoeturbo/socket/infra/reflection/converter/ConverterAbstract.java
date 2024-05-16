package com.dandev.tictactoeturbo.socket.infra.reflection.converter;

public abstract class ConverterAbstract<T> {

    public ConverterAbstract() {
    }

    public abstract T execute(String obj);
}