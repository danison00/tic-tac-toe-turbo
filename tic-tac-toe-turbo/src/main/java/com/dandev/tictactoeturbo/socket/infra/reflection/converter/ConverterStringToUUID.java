package com.dandev.tictactoeturbo.socket.infra.reflection.converter;

import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.Converter;

import java.util.UUID;

@Converter
public class ConverterStringToUUID extends ConverterAbstract<UUID> {

    @Override
    public UUID execute(String obj) {
        return UUID.fromString((String) obj);
    }
}