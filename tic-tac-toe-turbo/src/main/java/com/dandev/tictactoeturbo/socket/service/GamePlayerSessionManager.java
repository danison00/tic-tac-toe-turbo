package com.dandev.tictactoeturbo.socket.service;

import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

public interface GamePlayerSessionManager {

    void newSession(WebSocketSession session, UUID userId);

    void closeSession(WebSocketSession session);

    void  closeSession(UUID id);


}
