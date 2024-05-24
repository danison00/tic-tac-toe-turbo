package com.dandev.tictactoeturbo.socket.infra.enums;

import lombok.Getter;

@Getter
public enum ResponseStatusCode {
    OK("OK"),
    CREATED("Created"),
    ACCEPTED("Accepted"),
    NO_CONTENT("No Content"),
    MOVED_PERMANENTLY("Moved Permanently"),
    FOUND("Found"),
    BAD_REQUEST("Bad Request"),
    UNAUTHORIZED("Unauthorized"),
    FORBIDDEN("Forbidden"),
    NOT_FOUND("Not Found"),
    INTERNAL_SERVER_ERROR("Internal Server Error"),
    SERVICE_UNAVAILABLE("Service Unavailable"),


    //    MOVE,
//    SIMPLE_MESSAGE,
//    NEW_GAME_REQUEST,
//    USER_DATA,
//    NEW_CONNECTION,
//    CONNECTION_CLOSE,
//    PLAYER_WINS,
//    USER_NOT_ONLINE,
    USERS_ONLINE("USERS_ONLINE"),
    NEW_CHALLENGE("NEW_CHALLENGE"),
    NEW_GAME("NEW_GAME"),
    GET_GAME("GET_GAME"),
    ERROR("ERROR"),
    NEW_MESSAGE("NEW_MESSAGE"),
    ALL_MESSAGES("ALL_MESSAGES"),
    USER_CONNECTED("USER_CONNECTED"),
    USER_DESCONNECTED("USER_DESCONNECTED");
//    NEW_GAME_ACCEPT,
//    GET_GAME

    private final String message;

    ResponseStatusCode(String message) {
        this.message = message;
    }

}
