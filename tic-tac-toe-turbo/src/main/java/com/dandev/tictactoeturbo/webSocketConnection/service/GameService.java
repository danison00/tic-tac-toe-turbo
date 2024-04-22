package com.dandev.tictactoeturbo.webSocketConnection.service;

import com.dandev.tictactoeturbo.webSocketConnection.infra.exceptions.UserNotOnline;
import com.dandev.tictactoeturbo.webSocketConnection.dtos.Event;

import java.util.UUID;

public interface GameService {

    void newGame( Event messageSender) throws UserNotOnline;

    public void makeMove(UUID id, Event event) ;

    public void newGameRequest(UUID id, Event event);

    void getGame(Event event);
}
