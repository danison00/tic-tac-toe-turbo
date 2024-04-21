package com.dandev.tictactoeturbo.model;

import java.io.Serializable;

public enum EventType implements Serializable {
    MOVE,
    SIMPLE_MESSAGE,
    NEW_GAME_REQUEST,
    USER_DATA,
    CONNECTION,
    PLAYER_WINS,
    USER_NOT_ONLINE, GET_USERS_ONLINE,
    NEW_GAME_ACCEPT,
    NEW_GAME,
    GET_GAME

}
