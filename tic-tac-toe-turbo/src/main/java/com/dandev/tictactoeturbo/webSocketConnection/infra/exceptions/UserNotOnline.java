package com.dandev.tictactoeturbo.webSocketConnection.infra.exceptions;

import java.util.UUID;

public class UserNotOnline extends WebSocketExceptionAbstract {


    public UserNotOnline(String message, UUID id) {
        super(message, id);
    }
    public UserNotOnline(String message) {
        super(message);
    }
}
