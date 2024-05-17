package com.dandev.tictactoeturbo.socket.service.implementations;

import com.dandev.tictactoeturbo.socket.dtos.GameDto;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GamePlayerManager {

    private final Map<KeyGame, GameDto> gameByPlayers = new ConcurrentHashMap<>();
    private final Map<UUID, GameDto> gamesById = new ConcurrentHashMap<>();


    public void put(GameDto game) {
        gameByPlayers.put(KeyGame.of(game.player1().id(), game.player2().id()), game);
        gamesById.put(game.id(), game);
    }

    public void remove(UUID gameId) {
        var gameDto = gamesById.remove(gameId);
        if(gameDto != null)
            remove(gameDto.player1().id(), gameDto.player2().id());
    }

    public void remove(UUID idPlayer1, UUID idPlayer2) {
        GameDto remove = gameByPlayers.remove(KeyGame.of(idPlayer1, idPlayer2));
        if(remove == null)
            gameByPlayers.remove(KeyGame.of(idPlayer2, idPlayer1));
    }

    public Optional<GameDto> get(UUID gameId) {
        GameDto gameDto = gamesById.get(gameId);
        if(gameDto == null) return Optional.empty();
        return Optional.of(gameDto);
    }

    public Optional<GameDto> get(UUID idPlayer1, UUID idPlayer2) {

        GameDto gameDto = gameByPlayers.get(KeyGame.of(idPlayer1, idPlayer2));
        if(gameDto == null)
            gameDto = gameByPlayers.get(KeyGame.of(idPlayer2, idPlayer1));

        if (gameDto == null) return Optional.empty();

        return Optional.of(gameDto);
    }

    private record KeyGame(
            UUID player1,
            UUID player2
    ) {
        private static KeyGame of(UUID key1, UUID key2) {
            return new KeyGame(key1, key2);
        }
    }
}
