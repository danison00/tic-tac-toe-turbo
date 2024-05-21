package com.dandev.tictactoeturbo.socket.dtos;

import java.util.Map;
import java.util.UUID;

public record PlayerDto(
        UUID id,
        String name,
        String marker

) {
}
