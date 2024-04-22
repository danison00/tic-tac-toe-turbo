package com.dandev.tictactoeturbo.webSocketConnection.dtos;


import java.io.Serializable;
import java.util.UUID;

public record Event<T>(
        UUID idSender,
        UUID idReceiver,
        T payload,
        EventType type) implements Serializable {

    public Event setPaylooad(Object payload) {
        return new Event(idSender, idReceiver, payload, type);
    }
}
