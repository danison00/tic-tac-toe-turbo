package com.dandev.tictactoeturbo.socket.shared;

import com.dandev.tictactoeturbo.socket.dtos.UserView;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Service
public class UserOnlineManager {

    private final Map<UUID, UserOnline> users = new HashMap<>();

    public Optional<UserOnline> getById(UUID id) {
        UserOnline userOn = users.get(id);
        if (userOn == null) return Optional.empty();

        return Optional.of(userOn);
    }

    public void put(UUID id, String name, WebSocketSession session) {
        users.put(id, new UserOnline(id, name, session));
    }

    public void remove(UUID id) {

        var user = users.remove(id);
        if (user != null) {
            try {
                user.session.close();
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

    public record UserOnline(UUID id, String name, WebSocketSession session) {

    }
}
