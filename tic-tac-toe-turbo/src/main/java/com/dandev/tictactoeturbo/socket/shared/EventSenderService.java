package com.dandev.tictactoeturbo.socket.shared;

import com.dandev.tictactoeturbo.socket.dtos.Event;
import org.springframework.web.socket.WebSocketSession;

public interface EventSenderService {

    void sendEvent( Event event, WebSocketSession session);
}
