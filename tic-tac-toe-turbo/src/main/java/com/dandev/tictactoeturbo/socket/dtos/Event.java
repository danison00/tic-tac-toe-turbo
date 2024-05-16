package com.dandev.tictactoeturbo.socket.dtos;


import com.dandev.tictactoeturbo.util.URI;
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
    public Event setIdReceiver(UUID id){
        return new Event( idSender, id, payload, type);
    }
    public Event setIdSender(UUID id){
        return new Event( id, idReceiver, payload, type);
    }
}
