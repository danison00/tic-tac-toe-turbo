package com.dandev.tictactoeturbo.webSocketConnection.infra.webSocketHandler;

import com.dandev.tictactoeturbo.webSocketConnection.infra.exceptions.UserNotOnline;
import com.dandev.tictactoeturbo.model.*;
import com.dandev.tictactoeturbo.webSocketConnection.service.GameService;
import com.dandev.tictactoeturbo.webSocketConnection.service.UserOnlineService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.logging.Logger;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final UserOnlineService userOnlineService;
    private final GameService gameService;
    private final static Logger LOGGER = Logger.getLogger(WebSocketHandler.class.getName());
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WebSocketHandler(UserOnlineService userOnlineService, GameService gameService) {
        this.userOnlineService = userOnlineService;
        this.gameService = gameService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        userOnlineService.newConection(session);
    }


    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws JsonProcessingException, UserNotOnline {


        Event event = objectMapper.readValue(message.getPayload().toString(), Event.class);

        LOGGER.info(session.getId() + " ->  " + event+"WebSocketHandler.java:41");

        switch (event.type()) {
            case NEW_GAME_REQUEST -> {
                gameService.newGameRequest(event.idReceiver(), event);
                break;
            }
            case GET_USERS_ONLINE -> {
                userOnlineService.getAll(event.idSender());
                break;
            }
            case NEW_GAME_ACCEPT -> {
                gameService.newGame(event);
                break;
            }
            case GET_GAME -> {
                this.gameService.getGame(event);
                    break;
            }
            case MOVE -> {
                gameService.makeMove(
                        event.idReceiver(),
                        objectMapper.readValue(message.getPayload().toString(), new TypeReference<Event<Move>>() {
                        })
                );
                break;
            }
            default -> {

            }
        }


    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        userOnlineService.turnOffline(session);
    }

}
