package com.dandev.tictactoeturbo.util;

import com.dandev.tictactoeturbo.socket.dtos.Request;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.UUID;

public class RequestDeserializer extends JsonDeserializer<Request<String>> {
    @Override
    public Request<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String _uri = node.get("uri").asText();
        String _method = node.get("verb").asText();
        JsonNode bodyNode = node.get("body");
        String body = null;
        if (bodyNode != null) {
            body = ((ObjectMapper) jsonParser.getCodec()).writeValueAsString(node.get("body"));
        }

        return new Request<>(new URI(_uri), WebSocketVerb.valueOf(_method), body);
    }
}
