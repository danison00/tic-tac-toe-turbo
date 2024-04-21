package com.dandev.tictactoeturbo.webSocketConnection.service;

import com.dandev.tictactoeturbo.webSocketConnection.infra.exceptions.UserNotOnline;
import com.dandev.tictactoeturbo.model.Event;
import com.dandev.tictactoeturbo.model.entity.User;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

public interface UserOnlineService {

    User get(UUID id) throws UserNotOnline;

    void newConection(WebSocketSession session);

    void getAll(UUID id);

    void sendEvent(UUID id, Event event);
    void turnOffline(WebSocketSession session);

}
