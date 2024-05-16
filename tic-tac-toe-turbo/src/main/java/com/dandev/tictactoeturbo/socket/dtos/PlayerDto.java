package com.dandev.tictactoeturbo.socket.dtos;

import java.util.Map;
import java.util.UUID;

public record PlayerDto(
        UUID id,
        String name,
        String iconPlayer,
        Map<UUID, GameDto> games

) {
    PlayerDto setGames(Map< UUID,GameDto> games){
        return new PlayerDto(id(), name(), iconPlayer(), games);
    }
}
