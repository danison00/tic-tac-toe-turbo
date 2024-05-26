package com.dandev.tictactoeturbo.socket.controllers;

import com.dandev.tictactoeturbo.socket.dtos.UserView;
import com.dandev.tictactoeturbo.socket.infra.classes.Response;
import com.dandev.tictactoeturbo.socket.infra.enums.ResponseStatusCode;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.Get;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.RequestParam;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.WebSocketController;
import com.dandev.tictactoeturbo.socket.shared.ResponseSenderService;
import com.dandev.tictactoeturbo.socket.shared.UserOnlineManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.dandev.tictactoeturbo.socket.shared.UserOnlineManager.UserOnline;

@WebSocketController("/topic/users-online")
public class AllUserOnlineTopic {

    private final UserOnlineManager userOnlineManager;
    @Autowired
    private ResponseSenderService responseService;

    private static final Map<UUID, UserOnline> subscriptions = new ConcurrentHashMap<>();


    public AllUserOnlineTopic(UserOnlineManager userOnlineManager) {

        this.userOnlineManager = userOnlineManager;
        this.userOnlineManager.observerUsersOnline((allUsersOnline) -> {
            subscriptions.forEach((uuidSubscriber, subscriber) -> {

                    responseService.send(
                            Response.idReceiver(uuidSubscriber)
                                    .body(allUsersOnline.stream().map(userOn -> new UserView(userOn.id(), userOn.name())).filter((userview)->!userview.id().equals(uuidSubscriber)).toList())
                                    .status(ResponseStatusCode.USERS_ONLINE)
                    );
            });
            System.out.println("Enviando users online -> "+allUsersOnline);
        });
    }


    @Get
    public Response<List<UserView>> getAllUsers(@RequestParam UUID userId) {
        Optional<UserOnline> userOnlineOpt = userOnlineManager.getById(userId);
        if (userOnlineOpt.isEmpty())
            return null;

        subscriptions.put(userId, userOnlineOpt.get());
        System.out.println("User subscribe in AllUserTopic(AllUserOnlineTopic.java:52)");
        return Response
                .idReceiver(userId)
                .body(userOnlineManager.getAll()
                        .stream()
                        .filter(userView -> !userView.id().equals(userId))
                        .toList())
                .status(ResponseStatusCode.USERS_ONLINE);

    }

}
