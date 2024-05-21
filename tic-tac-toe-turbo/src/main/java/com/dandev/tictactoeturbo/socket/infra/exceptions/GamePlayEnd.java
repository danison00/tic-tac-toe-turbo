package com.dandev.tictactoeturbo.socket.infra.exceptions;

public class GamePlayEnd extends RuntimeException {

    public GamePlayEnd(){
        super("Jogo já finalizado.");
    }
}
