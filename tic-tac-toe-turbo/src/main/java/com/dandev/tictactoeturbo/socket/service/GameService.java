package com.dandev.tictactoeturbo.socket.service;

import com.dandev.tictactoeturbo.infra.exceptions.GameNotFound;
import com.dandev.tictactoeturbo.socket.dtos.Game;
import com.dandev.tictactoeturbo.socket.dtos.Move;

import java.util.Optional;
import java.util.UUID;

public interface GameService {

    Game newGame(UUID player1, UUID player2);

    Optional<Game> getByPlayers(UUID playerId, UUID player2id);

    Optional<Game> getById(UUID gameId) throws GameNotFound;

    Game makeMove(Move move);
}
