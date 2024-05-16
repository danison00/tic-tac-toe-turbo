package com.dandev.tictactoeturbo.socket.dtos;

import com.dandev.tictactoeturbo.util.RequestDeserializer;
import com.dandev.tictactoeturbo.util.URI;
import com.dandev.tictactoeturbo.util.WebSocketVerb;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


import java.io.Serializable;
import java.util.UUID;

@JsonDeserialize(using = RequestDeserializer.class)
public record Request<T>(
        URI uri,
        WebSocketVerb verb,
        T body
) implements Serializable {
}
