package com.dandev.tictactoeturbo.socket.shared;

import com.dandev.tictactoeturbo.socket.dtos.UserView;
import com.dandev.tictactoeturbo.socket.infra.enums.UserOnlineStatus;
import com.dandev.tictactoeturbo.socket.infra.pattern.AbstractObservableUserManager;
import com.dandev.tictactoeturbo.socket.infra.pattern.Observable;
import com.dandev.tictactoeturbo.socket.infra.pattern.Subscriber;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserOnlineManager extends AbstractObservableUserManager<UserOnlineManager.UserOnline> {

    private final Map<UUID, UserOnline> users = new ConcurrentHashMap<>();


    public Optional<UserOnline> getById(UUID id) {
        UserOnline userOn = users.get(id);
        if (userOn == null) return Optional.empty();

        return Optional.of(userOn);
    }

    public void put(UUID id, String name, WebSocketSession session) {
        var userOnline = new UserOnline(id, name, session, UserOnlineStatus.ONLINE);
        users.put(id, userOnline);
        notifyObserversSingleUser(userOnline);
        notifyObserversListUser(this.users.values().stream().toList());

    }

    public void remove(UUID id) {

        var userOnline = users.remove(id);
        if (userOnline != null) {
            try {
                userOnline.session.close();
                var userOffline = new UserOnline(userOnline.id, userOnline.name(), null, UserOnlineStatus.OFFLINE);
                notifyObserversSingleUser(userOffline);
                notifyObserversListUser(this.users.values().stream().toList());
            } catch (IOException e) {
            }
        }

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
