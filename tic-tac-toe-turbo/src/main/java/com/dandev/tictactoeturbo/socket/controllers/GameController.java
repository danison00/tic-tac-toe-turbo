package com.dandev.tictactoeturbo.socket.controllers;

import com.dandev.tictactoeturbo.infra.exceptions.GameNotFound;
import com.dandev.tictactoeturbo.socket.dtos.GameDto;
import com.dandev.tictactoeturbo.socket.dtos.Move;
import com.dandev.tictactoeturbo.socket.dtos.UserView;
import com.dandev.tictactoeturbo.socket.service.GameService;
import com.dandev.tictactoeturbo.socket.infra.classes.Response;
import com.dandev.tictactoeturbo.socket.infra.enums.ResponseStatusCode;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@WebSocketController("/game")
public class GameController {

    @Autowired
    private GameService gameService;


    @Get
    public List<Response<UserView>> getGames(@RequestParam UUID id) {

        ArrayList<Response<UserView>> responses = new ArrayList<>(2);
        responses.add(
                Response
                        .idReceiver(UUID.randomUUID())
                        .idSender(UUID.randomUUID())
                        .body(new UserView(UUID.randomUUID(), "Danison"))
                        .status(ResponseStatusCode.OK)

        );
        responses.add(
                Response
                        .idReceiver(UUID.randomUUID())
                        .idSender(UUID.randomUUID())
                        .body(new UserView(UUID.randomUUID(), "Joao"))
                        .status(ResponseStatusCode.OK)
        );

        return responses;
    }

    @Post
    public List<Response<UUID>> newGame(@RequestParam UUID userId, @RequestParam UUID player2Id) {

        GameDto gameDto = gameService.newGame(userId, player2Id);
        return List.of(
                Response.idReceiver(userId).body(gameDto.id()).status(ResponseStatusCode.NEW_GAME),
                Response.idReceiver(player2Id).body(gameDto.id()).status(ResponseStatusCode.NEW_GAME)
        );

    }

    @Get("/single")
    public Response<GameDto> getById(@RequestParam UUID userId, @RequestParam UUID gameId) throws GameNotFound{
        Optional<GameDto> gameDtoOpt = gameService.getById(gameId);
        return gameDtoOpt.map(gameDto -> Response.idReceiver(userId).body(gameDto).status(ResponseStatusCode.GET_GAME)).orElse(null);
    }

    @Post("/move")
    public List<Response<GameDto>> makeMove(@RequestParam UUID userId, @RequestBody Move move){
       GameDto game = gameService.makeMove(move);

       return List.of(
               Response.idReceiver(game.player1().id()).body(game).status(ResponseStatusCode.GET_GAME),
               Response.idReceiver(game.player2().id()).body(game).status(ResponseStatusCode.GET_GAME)
       );
    }


}
