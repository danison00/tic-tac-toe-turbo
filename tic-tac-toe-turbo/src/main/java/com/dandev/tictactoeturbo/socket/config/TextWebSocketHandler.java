package com.dandev.tictactoeturbo.socket.config;

import com.dandev.tictactoeturbo.socket.dtos.Request;
import com.dandev.tictactoeturbo.socket.infra.exceptions.UserIdNotIndetifier;
import com.dandev.tictactoeturbo.socket.infra.reflection.controller.ControllerProcessor;
import com.dandev.tictactoeturbo.socket.shared.ResponseSenderService;
import com.dandev.tictactoeturbo.util.JsonConverter;
import com.dandev.tictactoeturbo.util.URI;
import com.dandev.tictactoeturbo.util.WebSocketVerb;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Configuration
public class TextWebSocketHandler extends org.springframework.web.socket.handler.TextWebSocketHandler {

    @Autowired
    private ControllerProcessor controllerProcessor;

    @Autowired
    private ResponseSenderService responseSenderService;

    @Autowired
    private JsonConverter jsonConverter;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String idString = this.parseCookies(session, "user_id");
        if (idString == null) throw new UserIdNotIndetifier();

        try {

            controllerProcessor.handle(new Request<>(new URI("/user/connection?userId=" + idString), WebSocketVerb.POST, session));
        } catch (Throwable e) {
            System.out.println(e);
        }
    }


    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws JsonProcessingException {


        String idString = this.parseCookies(session, "user_id");
        if (idString == null) throw new UserIdNotIndetifier();
        Request<?> request = jsonConverter.deserialize(message.getPayload().toString(), Request.class);
        try {
            request.uri().addParam("userId", idString);
            Optional<Object> responseOpt = controllerProcessor.handle(request);
            responseOpt.ifPresent((response) -> responseSenderService.send(response));
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String idString = this.parseCookies(session, "user_id");
        if (idString == null) throw new UserIdNotIndetifier();
        try {
            controllerProcessor.handle(new Request<>(new URI("/user/connection?userId=" + idString), WebSocketVerb.DELETE, session));
            System.out.println("User desconected -> " + idString + "(TextWebSocketHandler.java:59)");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private String parseCookies(WebSocketSession session, String cookieName) {
        List<String> cookieList = session.getHandshakeHeaders().get("cookie");
        if (cookieList == null) return null;

        String cookieString = cookieList.get(0);
        Map<String, String> cookies = new HashMap<>();

        if (cookieString == null || cookieString.isEmpty()) {
            return null;
        }

        // Split the string by the semicolon (;) to get individual cookies
        String[] cookiePairs = cookieString.split(";");

        for (String cookiePair : cookiePairs) {
            // Split each cookie pair by the equals sign (=)
            String[] keyValue = cookiePair.split("=", 2);

            if (keyValue.length == 2) {
                // Trim leading and trailing spaces from the key and value
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                cookies.put(key, value);
            }
        }

        return cookies.get(cookieName);
    }

}
