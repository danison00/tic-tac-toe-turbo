package com.dandev.tictactoeturbo.socket.service.implementations;

import com.dandev.tictactoeturbo.infra.exceptions.GameNotFound;
import com.dandev.tictactoeturbo.model.entity.User;
import com.dandev.tictactoeturbo.service.UserService;
import com.dandev.tictactoeturbo.socket.dtos.Move;
import com.dandev.tictactoeturbo.socket.enums.GamePlayStatus;
import com.dandev.tictactoeturbo.socket.dtos.GameDto;
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
    public GameDto newGame(UUID player1Id, UUID player2Id) {


        Optional<GameDto> gameDtoOpt = gamePlayerManager.get(player1Id, player2Id);

        if (gameDtoOpt.isPresent())
            return gameDtoOpt.get();

        Optional<User> user1Opt = userService.findById(player1Id);
        Optional<User> user2Opt = userService.findById(player2Id);
        if (user1Opt.isEmpty() && user2Opt.isEmpty())
            return null;

        GameDto game = GameDto.newGame(user1Opt.get().getId(), user1Opt.get().getName(), user2Opt.get().getId(), user2Opt.get().getName());
        gamePlayerManager.put(game);

        return game;
    }


    @Override
    public Optional<GameDto> getByPlayers(UUID player1Id, UUID player2Id) {
        return gamePlayerManager.get(player1Id, player2Id);
    }


    @Override
    public Optional<GameDto> getById(UUID gameId) {
        return gamePlayerManager.get(gameId);
    }

    @Override
    public GameDto makeMove(Move move) {

        Optional<GameDto> gameDtoOpt = gamePlayerManager.get(move.idGame());
        GameDto gameDto = gameDtoOpt.orElseThrow(GameNotFound::new);
        gameDto = gameDto.makeMove(move);
        if(gameDto.status().equals(GamePlayStatus.WIN) || gameDto.status().equals(GamePlayStatus.NO_WINS)){
            gamePlayerManager.remove(gameDto.id());
            return gameDto;
        }
        gamePlayerManager.put(gameDto);
        return gameDto;
    }

}

