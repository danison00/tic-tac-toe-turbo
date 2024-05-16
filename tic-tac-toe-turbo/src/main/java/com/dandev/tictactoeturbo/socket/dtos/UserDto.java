package com.dandev.tictactoeturbo.socket.dtos;

import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record UserDto (UUID id, String name, Optional<LocalDateTime> lastPing, WebSocketSession session){
}
