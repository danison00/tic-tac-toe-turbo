package com.dandev.tictactoeturbo.webSocketConnection.service;

import com.dandev.tictactoeturbo.model.entity.User;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.UUID;

public interface SessionService {

    void put(UUID id, WebSocketSession session);

    WebSocketSession get(UUID id);

    void remove(UUID id);

}
