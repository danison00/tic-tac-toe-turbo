package com.dandev.tictactoeturbo.infra.exceptions;

public class GameNotFound extends RuntimeException {
    public GameNotFound() {
        super("Jogo não encontrado.");
    }
}
