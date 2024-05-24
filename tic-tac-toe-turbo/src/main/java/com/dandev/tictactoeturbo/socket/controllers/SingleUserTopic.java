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
import  static  com.dandev.tictactoeturbo.socket.shared.UserOnlineManager.UserOnline;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@WebSocketController("/topic/single/user")
public class SingleUserTopic {


    private final UserOnlineManager userOnlineManager;
    @Autowired
    private ResponseSenderService responseService;

    private static final Map<UserOnline, UUID> subscriptions = new ConcurrentHashMap<>();



    public SingleUserTopic(UserOnlineManager userOnlineManager) {

        this.userOnlineManager = userOnlineManager;
        this.userOnlineManager.observerAll().subscribe((usersOnline) -> {
            subscriptions.forEach((userObserver, userIdObservable) -> {

                if(!userObserver.session().isOpen()){
                    subscriptions.remove(userObserver);
                    return;
                }
                Optional<UserView> userViewOpt = usersOnline.stream().filter(userView -> userView.id().equals(userIdObservable)).findFirst();
                if (userViewOpt.isPresent()) {
                    responseService.send(Response.idReceiver(userObserver.id()).body("").status(ResponseStatusCode.USER_CONNECTED));
                }else {
                    responseService.send(Response.idReceiver(userObserver.id()).body("").status(ResponseStatusCode.USER_DESCONNECTED));

                }

            });
        });
    }

    @Get
    public Response<?> subscribe(@RequestParam UUID userId, @RequestParam UUID userIdObservable){

        Optional<UserOnline> userOpt = userOnlineManager.getById(userId);
        if(userOpt.isEmpty()) return null;
        subscriptions.put(userOpt.get(), userIdObservable);

        Optional<UserOnline> userObservableOpt = userOnlineManager.getById(userIdObservable);


        if (userObservableOpt.isPresent()) {
          return   Response.idReceiver(userId).body("").status(ResponseStatusCode.USER_CONNECTED);
        }else {
           return Response.idReceiver(userId).body("").status(ResponseStatusCode.USER_DESCONNECTED);

        }
    }

}
