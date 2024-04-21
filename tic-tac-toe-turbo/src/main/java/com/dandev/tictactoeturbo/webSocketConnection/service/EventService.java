package com.dandev.tictactoeturbo.webSocketConnection.service;

import com.dandev.tictactoeturbo.model.Event;
import org.springframework.web.socket.WebSocketSession;

public interface EventService {

    void sendEvent( Event event, WebSocketSession session);
}
