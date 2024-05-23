package com.dandev.tictactoeturbo.socket.controllers;

import com.dandev.tictactoeturbo.infra.exceptions.GameNotFound;
import com.dandev.tictactoeturbo.socket.dtos.Game;
import com.dandev.tictactoeturbo.socket.dtos.Message;
import com.dandev.tictactoeturbo.socket.infra.classes.Response;
import com.dandev.tictactoeturbo.socket.infra.enums.ResponseStatusCode;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.*;
import com.dandev.tictactoeturbo.socket.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@WebSocketController("/message")
public class MessageController {

    @Autowired
    private GameService gameService;

    @Post
    public Response<Message> sendMessage(@RequestBody Message message, @RequestParam UUID gameId) {

        Game game = gameService.getById(gameId).orElseThrow(GameNotFound::new);
        game.getMessages().add(message);
        return Response.idReceiver(message.getIdReceiver()).idSender(message.getIdSender()).body(message).status(ResponseStatusCode.NEW_MESSAGE);

    }

    @Get
    public Response<List<Message>> getAll(@RequestParam UUID userId, @RequestParam UUID gameId){
        Game game = gameService.getById(gameId).orElseThrow(GameNotFound::new);
        return Response.idReceiver(userId).body(game.getMessages()).status(ResponseStatusCode.ALL_MESSAGES);
    }
}
