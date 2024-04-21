package com.dandev.tictactoeturbo.util;

import com.dandev.tictactoeturbo.webSocketConnection.infra.exceptions.IdUserNotPresent;
import com.dandev.tictactoeturbo.webSocketConnection.infra.exceptions.UriReferenceNull;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.util.UUID;

@Service
public class Mapper {

    private final ObjectMapper mapper = new ObjectMapper();

    public String serialize(Object value){
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public <T> T deserialize(String value, Class<T> clazz){
        try {
            return  mapper.readValue(value,clazz );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public UUID getUserIdFromURL(WebSocketSession session) {

        if (session.getUri() == null)
            throw new UriReferenceNull("A uri é null");
        Optional<String> id = UriComponentsBuilder.fromUri(session.getUri()).build().getQueryParams().get("id").stream().findFirst();
        if (id.isEmpty())
            throw new IdUserNotPresent("Id do usuário não  presente");


        return UUID.fromString(id.get());
    }

}
