package com.dandev.tictactoeturbo.util;

import com.dandev.tictactoeturbo.socket.dtos.Request;
import com.dandev.tictactoeturbo.socket.infra.exceptions.IdUserNotPresent;
import com.dandev.tictactoeturbo.socket.infra.exceptions.JsonDeserializerError;
import com.dandev.tictactoeturbo.socket.infra.exceptions.JsonSerializerError;
import com.dandev.tictactoeturbo.socket.infra.exceptions.UriReferenceNull;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class JsonConverter {

    private final ObjectMapper mapper = new ObjectMapper();

    public JsonConverter() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Request.class, new RequestDeserializer());
        mapper.registerModule(module);
    }

    public String serialize(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (Throwable e) {
            throw new JsonSerializerError(value, e);
        }
    }

    public <T> T deserialize(String value, Class<T> clazz) {
        try {
            return mapper.readValue(value, clazz);
        } catch (Throwable e) {
            throw new JsonDeserializerError(value, clazz, e);
        }
    }

    public <T> T deserialize(String value, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(value, typeReference);
        } catch (Throwable e) {
            throw new JsonDeserializerError(value, typeReference.getType().getTypeName(), e);
        }
    }

    public static UUID getUserIdFromURL(WebSocketSession session) {


        URI uri = new URI(session.getUri().toString());
        Optional<String> userId = uri.getParam("userId");

        String idStr = userId.orElseThrow(() -> new UriReferenceNull("URI é null"));


        return UUID.fromString(idStr);
    }
}

