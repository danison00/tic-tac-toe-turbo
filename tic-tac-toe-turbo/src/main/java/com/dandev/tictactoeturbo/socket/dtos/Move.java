package com.dandev.tictactoeturbo.socket.dtos;

import com.dandev.tictactoeturbo.model.entity.Player;

import java.io.Serializable;
import java.util.UUID;

public record Move(
        UUID idGame,
        PlayerDto player,
        int line,
        int column
) implements Serializable {

}
