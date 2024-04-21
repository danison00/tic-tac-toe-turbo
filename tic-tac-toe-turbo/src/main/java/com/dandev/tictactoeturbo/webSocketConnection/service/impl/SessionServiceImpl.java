package com.dandev.tictactoeturbo.webSocketConnection.service.impl;

import com.dandev.tictactoeturbo.webSocketConnection.infra.exceptions.SessionNotFound;
import com.dandev.tictactoeturbo.webSocketConnection.service.SessionService;
import com.dandev.tictactoeturbo.util.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionServiceImpl implements SessionService {

    private final Map<UUID, WebSocketSession> sessions = new ConcurrentHashMap<>();

    private final Mapper mapper;

    public SessionServiceImpl(Mapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public void put(UUID id, WebSocketSession session) {
        sessions.put(id, session);
    }

    @Override
    public WebSocketSession get(UUID id) {
        if (!sessions.containsKey(id)) throw new SessionNotFound("Sessão não encontrada.");
        return sessions.get(id);
    }


    @Override
    public void remove(UUID id) {

        sessions.remove(id);
    }


}
