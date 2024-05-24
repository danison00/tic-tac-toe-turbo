package com.dandev.tictactoeturbo.socket.controllers;

import com.dandev.tictactoeturbo.model.entity.User;
import com.dandev.tictactoeturbo.service.UserService;
import com.dandev.tictactoeturbo.socket.dtos.UserView;
import com.dandev.tictactoeturbo.socket.infra.classes.Response;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.*;
import com.dandev.tictactoeturbo.socket.shared.UserOnlineManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@WebSocketController("/user")
public class UserController {

    @Autowired
    private UserOnlineManager userOnlineManager;
    @Autowired
    private UserService userService;

    @Get
    public Response<List<UserView>> allUsersOnline(@RequestParam UUID userId){
//        return Response.idReceiver(userId).body(userOnlineManager.getAll().stream().filter((userview)->!userview.id().equals(userId)).toList()).status(ResponseStatusCode.USERS_ONLINE);
    return null;
    }


    @Post("/connection")
    public void newConnection(@RequestParam UUID userId, WebSocketSession session) {
        Optional<User> userOpt = userService.findById(userId);
        userOpt.ifPresent(user -> {
            userOnlineManager.put(user.getId(), user.getName(), session);
            System.out.println("usuario conectado " + userId);
        });
    }

    @Delete("/connection")
    public void closeConnection(@RequestParam UUID userId) {
        System.out.println("Desconectado -> "+userId );
        userOnlineManager.remove(userId);

    }



}
