package com.dandev.tictactoeturbo.socket.service.implementations;

import com.dandev.tictactoeturbo.socket.dtos.Game;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GamePlayerManager {

    private final Map<KeyGame, Game> gameByPlayers = new ConcurrentHashMap<>();
    private final Map<UUID, Game> gamesById = new ConcurrentHashMap<>();


    public void put(Game game) {
        gameByPlayers.put(KeyGame.of(game.getPlayer1().id(), game.getPlayer2().id()), game);
        gamesById.put(game.getId(), game);
    }

    public void remove(UUID gameId) {
        var gameDto = gamesById.remove(gameId);
        if(gameDto != null)
            remove(gameDto.getPlayer1().id(), gameDto.getPlayer2().id());
    }

    public void remove(UUID idPlayer1, UUID idPlayer2) {
        Game remove = gameByPlayers.remove(KeyGame.of(idPlayer1, idPlayer2));
        if(remove == null)
            gameByPlayers.remove(KeyGame.of(idPlayer2, idPlayer1));
    }

    public Optional<Game> get(UUID gameId) {
        Game gameDto = gamesById.get(gameId);
        if(gameDto == null) return Optional.empty();
        return Optional.of(gameDto);
    }

    public Optional<Game> get(UUID idPlayer1, UUID idPlayer2) {

        Game gameDto = gameByPlayers.get(KeyGame.of(idPlayer1, idPlayer2));
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
