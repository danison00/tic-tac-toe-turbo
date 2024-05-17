package com.dandev.tictactoeturbo.socket.controllers;

import com.dandev.tictactoeturbo.socket.dtos.UserView;
import com.dandev.tictactoeturbo.socket.infra.classes.Response;
import com.dandev.tictactoeturbo.socket.infra.enums.ResponseStatusCode;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.Post;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.RequestParam;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.WebSocketController;

import static com.dandev.tictactoeturbo.socket.shared.UserOnlineManager.UserOnline;

import com.dandev.tictactoeturbo.socket.shared.UserOnlineManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

@WebSocketController("/challenge")
public class ChallengeController {


    @Autowired
    private UserOnlineManager userOnlineManager;


    @Post
    public Response<?> newChallenge(@RequestParam UUID userId, @RequestParam UUID idPlayerReceiver) {

        Optional<UserOnline> playerSenderOpt = userOnlineManager.getById(userId);

        return playerSenderOpt.map(userOnline ->
                Response.idReceiver(idPlayerReceiver)
                        .idSender(userId)
                        .body(
                                new UserView(userOnline.id(),
                                        userOnline.name()))
                        .status(ResponseStatusCode.NEW_CHALLENGE))
                .orElse(null);

    }
}
