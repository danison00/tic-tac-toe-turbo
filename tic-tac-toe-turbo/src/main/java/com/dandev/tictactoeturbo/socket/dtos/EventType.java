package com.dandev.tictactoeturbo.socket.dtos;

import java.io.Serializable;

public enum EventType implements Serializable {
    MOVE,
    SIMPLE_MESSAGE,
    NEW_GAME_REQUEST,
    USER_DATA,
    NEW_CONNECTION,
    CONNECTION_CLOSE,
    PLAYER_WINS,
    USER_NOT_ONLINE,
    GET_USERS_ONLINE,
    NEW_GAME_ACCEPT,
    GET_GAME

}
