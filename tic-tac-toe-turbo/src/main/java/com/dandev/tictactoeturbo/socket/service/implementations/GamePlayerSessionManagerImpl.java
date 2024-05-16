package com.dandev.tictactoeturbo.socket.service.implementations;

import com.dandev.tictactoeturbo.socket.service.GamePlayerSessionManager;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GamePlayerSessionManagerImpl implements GamePlayerSessionManager {

    private Map<UUID, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void newSession(WebSocketSession session, UUID userId) {
        sessions.put(userId, session);
    }

    @Override
    public void closeSession(WebSocketSession session) {
        try {
            session.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void closeSession(UUID id) {
        closeSession(sessions.get(id));
    }
    public Optional<WebSocketSession> get(UUID id){
        return  Optional.empty();
    }
}
