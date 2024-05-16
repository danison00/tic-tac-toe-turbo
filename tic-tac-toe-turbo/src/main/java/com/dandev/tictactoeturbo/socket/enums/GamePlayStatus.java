package com.dandev.tictactoeturbo.socket.enums;

public enum GamePlayStatus {
    NO_TOUCH("NO_TOUCH"),
    PLAYING("PLAYING"),
    NO_WINS("NO_WINS"),
    WIN("WIN");

    GamePlayStatus(String status) {
    }
}
