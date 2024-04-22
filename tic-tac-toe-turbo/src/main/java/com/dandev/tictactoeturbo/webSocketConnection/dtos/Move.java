package com.dandev.tictactoeturbo.webSocketConnection.dtos;

import com.dandev.tictactoeturbo.model.entity.Player;

import java.io.Serializable;
import java.util.UUID;

public record Move(
        UUID idGame,
        Player player,
        int line,
        int column,
        String[][] board
) implements Serializable {

    public Move setBoard(String[][] board){
        return  new Move(idGame, player, line, column, board);
    }
}
