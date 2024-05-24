package com.dandev.tictactoeturbo.socket.infra.pattern;

@FunctionalInterface
public interface Subscriber<T> {

    void execute(T t);
}
