package com.dandev.tictactoeturbo.socket.dtos;

import com.dandev.tictactoeturbo.socket.enums.GamePlayStatus;
import lombok.Setter;

import java.util.UUID;


public record GameDto(
        UUID id,
        PlayerDto player1,
        PlayerDto player2,
        PlayerDto playerCurrent,
        PlayerDto playerWins,
        GamePlayStatus status,
        String board
) {
    public GameDto tooglePlayerCurrent() {
        return new GameDto(
                id,
                player1,
                player2,
                playerCurrent.equals(player1) ? player2 : player1,
                playerWins,
                status,
                board
        );
    }

    public GameDto setBoard(String board) {
        return new GameDto(
                id,
                player1,
                player2,
                playerCurrent,
                playerWins,
                status,
                board
        );
    }
    public GameDto setStatus(GamePlayStatus status){
        return new GameDto(
                id,
                player1,
                player2,
                playerCurrent,
                playerWins,
                status,
                board
        );
    }
    public GameDto setPlayerWins(PlayerDto playerWins){
        return new GameDto(
                id,
                player1,
                player2,
                playerCurrent,
                playerWins,
                status,
                board
        );
    }

}
