package com.dandev.tictactoeturbo.socket.shared;

import com.dandev.tictactoeturbo.socket.dtos.Event;
import com.dandev.tictactoeturbo.util.JsonConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Service
public class EventSenderServiceImpl implements EventSenderService {

    private final JsonConverter jsonConverter;

    EventSenderServiceImpl(JsonConverter jsonConverter){
        this.jsonConverter = jsonConverter;
    }
    @Override
    public void sendEvent( Event event, WebSocketSession session) {
        String eventSerialized = jsonConverter.serialize(event);
        try {
            session.sendMessage(new TextMessage(eventSerialized));
            System.out.println("Event sent -> "+event.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
