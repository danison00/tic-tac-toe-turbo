package com.dandev.tictactoeturbo.webSocketConnection.infra.handlerExeptionAdvice;

import com.dandev.tictactoeturbo.webSocketConnection.infra.exceptions.UserNotOnline;
import com.dandev.tictactoeturbo.webSocketConnection.dtos.Event;
import com.dandev.tictactoeturbo.webSocketConnection.dtos.EventType;
import com.dandev.tictactoeturbo.webSocketConnection.WebSocketEventsHandler;
import com.dandev.tictactoeturbo.webSocketConnection.service.GameService;
import com.dandev.tictactoeturbo.webSocketConnection.service.UserOnlineService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

@Component
public class WebSocketHandlerExceptionAdvice extends WebSocketEventsHandler {

    private final UserOnlineService userOnlineService;
    WebSocketHandlerExceptionAdvice(UserOnlineService userOnlineService, GameService gameService) {
        super(userOnlineService, gameService);
        this.userOnlineService = userOnlineService;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws JsonProcessingException {

        try {
            super.handleMessage(session, message);
        } catch (Throwable e) {

            if (e instanceof UserNotOnline userNotOnline) {
                UUID  id = userNotOnline.getId();
                Event event = new Event(null, id, null, EventType.USER_NOT_ONLINE);
               userOnlineService.sendEvent(id, event);
            }
          else {
               throw e;
            }

        }
    }


}
