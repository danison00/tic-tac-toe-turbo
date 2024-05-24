package com.dandev.tictactoeturbo.socket.shared;

import com.dandev.tictactoeturbo.socket.dtos.UserView;
import com.dandev.tictactoeturbo.socket.infra.enums.UserOnlineStatus;
import com.dandev.tictactoeturbo.socket.infra.pattern.Observable;
import com.dandev.tictactoeturbo.socket.infra.pattern.Subscriber;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserOnlineManager {

    private final Map<UUID, UserOnline> users = new ConcurrentHashMap<>();
    private final Observable<List<UserView>> $users = new Observable<>();
    private final Observable<UserOnline> $singleUser = new Observable<>();


    public Optional<UserOnline> getById(UUID id) {
        UserOnline userOn = users.get(id);
        if (userOn == null) return Optional.empty();

        return Optional.of(userOn);
    }

    public void put(UUID id, String name, WebSocketSession session) {
        users.put(id, new UserOnline(id, name, session, UserOnlineStatus.ONLINE));
        this.$users.next(users.values().stream().map(userOnline -> new UserView(userOnline.id(), userOnline.name())).toList());
    }

    public void remove(UUID id) {

        var user = users.remove(id);
        if (user != null) {
            try {
                user.session.close();
            } catch (IOException e) {
            }
        }
        this.$users.next(users.values().stream().map(userOnline -> new UserView(userOnline.id(), userOnline.name())).toList());

    }
    public Observable<UserOnline> observerSingleUser(UUID id){

        return this.$singleUser;
    }

    public Observable<List<UserView>> observerAll() {

        return this.$users;
//        return users.values()
//                .stream()
//                .map(userOnline -> new UserView(
//                        userOnline.id,
//                        userOnline.name))
//                .toList();
    }
    public List<UserView> getAll() {


        return users.values()
                .stream()
                .map(userOnline -> new UserView(
                        userOnline.id,
                        userOnline.name))
                .toList();
    }




    public record UserOnline(UUID id, String name, WebSocketSession session, UserOnlineStatus status) {

    }
}
