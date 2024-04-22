package com.dandev.tictactoeturbo.webSocketConnection.service;

import com.dandev.tictactoeturbo.webSocketConnection.dtos.Event;
import org.springframework.web.socket.WebSocketSession;

public interface EventService {

    void sendEvent( Event event, WebSocketSession session);
}
