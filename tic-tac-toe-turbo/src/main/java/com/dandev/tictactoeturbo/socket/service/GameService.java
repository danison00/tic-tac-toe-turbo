package com.dandev.tictactoeturbo.socket.service;

import com.dandev.tictactoeturbo.socket.dtos.GameDto;
import com.dandev.tictactoeturbo.socket.dtos.Move;

import java.util.Optional;
import java.util.UUID;

public interface GameService {

    GameDto newGame(UUID player1, UUID player2);

    Optional<GameDto> getByPlayers(UUID playerId, UUID player2id);

    Optional<GameDto> getById(UUID gameId);

    GameDto makeMove(Move move);
}
