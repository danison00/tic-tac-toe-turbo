package com.dandev.tictactoeturbo.webSocketConnection.service.impl;

import com.dandev.tictactoeturbo.webSocketConnection.dtos.Event;
import com.dandev.tictactoeturbo.webSocketConnection.service.EventService;
import com.dandev.tictactoeturbo.util.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Service
public class EventServiceImpl implements EventService {

    private final Mapper mapper;

    EventServiceImpl(Mapper mapper){
        this.mapper = mapper;
    }
    @Override
    public void sendEvent( Event event, WebSocketSession session) {
        String eventSerialized = mapper.serialize(event);
        try {
            session.sendMessage(new TextMessage(eventSerialized));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
