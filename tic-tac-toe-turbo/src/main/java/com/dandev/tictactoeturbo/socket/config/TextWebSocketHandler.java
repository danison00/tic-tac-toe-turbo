package com.dandev.tictactoeturbo.socket.config;

import com.dandev.tictactoeturbo.socket.dtos.Request;
import com.dandev.tictactoeturbo.socket.infra.exceptions.JsonDeserializerError;
import com.dandev.tictactoeturbo.socket.infra.reflection.controller.GatewayController;
import com.dandev.tictactoeturbo.socket.shared.ResponseSenderService;
import com.dandev.tictactoeturbo.util.JsonConverter;
import com.dandev.tictactoeturbo.util.URI;
import com.dandev.tictactoeturbo.util.WebSocketVerb;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class TextWebSocketHandler extends org.springframework.web.socket.handler.TextWebSocketHandler {

    @Autowired
    private GatewayController gatewayController;

    @Autowired
    private ResponseSenderService responseSenderService;

    @Autowired
    private JsonConverter jsonConverter;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        UUID userId = JsonConverter.getUserIdFromURL(session);
        gatewayController.handle(new Request<>(new URI("/user/connection?userId=" + userId), WebSocketVerb.POST, session));
    }


    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws JsonProcessingException {

        Request<?> request = null;

        request = jsonConverter.deserialize(message.getPayload().toString(), Request.class);
        Optional<Object> responseOpt = gatewayController.handle(request);
        responseOpt.ifPresent((response) -> responseSenderService.send(response));

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        UUID userId = JsonConverter.getUserIdFromURL(session);

        gatewayController.handle(new Request<>(new URI("/user/connection?userId=" + userId), WebSocketVerb.DELETE, session));
    }

}
