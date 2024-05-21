package com.dandev.tictactoeturbo.socket.service.implementations;

import com.dandev.tictactoeturbo.infra.exceptions.GameNotFound;
import com.dandev.tictactoeturbo.model.entity.User;
import com.dandev.tictactoeturbo.service.UserService;
import com.dandev.tictactoeturbo.socket.dtos.Game;
import com.dandev.tictactoeturbo.socket.dtos.Move;
import com.dandev.tictactoeturbo.socket.enums.GamePlayStatus;
import com.dandev.tictactoeturbo.socket.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private UserService userService;

    @Autowired
    private GamePlayerManager gamePlayerManager;

    @Override
    public Game newGame(UUID player1Id, UUID player2Id) {


        Optional<Game> gameDtoOpt = gamePlayerManager.get(player1Id, player2Id);

        if (gameDtoOpt.isPresent())
            return gameDtoOpt.get();

        Optional<User> user1Opt = userService.findById(player1Id);
        Optional<User> user2Opt = userService.findById(player2Id);
        if (user1Opt.isEmpty() && user2Opt.isEmpty())
            return null;

        Game game = new Game(user1Opt.get().getId(), user1Opt.get().getName(), user2Opt.get().getId(), user2Opt.get().getName());
        gamePlayerManager.put(game);

        return game;
    }


    @Override
    public Optional<Game> getByPlayers(UUID player1Id, UUID player2Id) {
        return gamePlayerManager.get(player1Id, player2Id);
    }


    @Override
    public Optional<Game> getById(UUID gameId) {
        return gamePlayerManager.get(gameId);
    }

    @Override
    public Game makeMove(Move move) {

        Optional<Game> gameDtoOpt = gamePlayerManager.get(move.idGame());
        Game game = gameDtoOpt.orElseThrow(GameNotFound::new);
        game.makeMove(move);

        if (game.isGameEnd())
            gamePlayerManager.remove(game.getId());

        return game;
    }

}

