package com.dandev.tictactoeturbo.webSocketConnection.service.impl;

import com.dandev.tictactoeturbo.webSocketConnection.dtos.EventType;
import com.dandev.tictactoeturbo.webSocketConnection.infra.exceptions.UserNotOnline;
import com.dandev.tictactoeturbo.webSocketConnection.dtos.Event;
import com.dandev.tictactoeturbo.model.entity.User;
import com.dandev.tictactoeturbo.repository.UserRespository;
import com.dandev.tictactoeturbo.webSocketConnection.service.EventService;
import com.dandev.tictactoeturbo.webSocketConnection.service.SessionService;
import com.dandev.tictactoeturbo.webSocketConnection.service.UserOnlineService;
import com.dandev.tictactoeturbo.util.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Service
public class UserOnlineServiceImpl implements UserOnlineService {
    private final Map<UUID, User> usersonline = new ConcurrentHashMap<>();

    private final EventService eventService;
    private final SessionService sessionService;
    private final UserRespository userRespository;
    private final Mapper mapper;
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public UserOnlineServiceImpl(EventService eventService, SessionService sessionService, UserRespository userRespository, Mapper mapper) {
        this.eventService = eventService;
        this.sessionService = sessionService;
        this.userRespository = userRespository;
        this.mapper = mapper;
    }

    @Override
    public User get(UUID id) throws UserNotOnline {
        if (!usersonline.containsKey(id)) throw new UserNotOnline("Usuário não está online.");
        return usersonline.get(id);
    }

    @Override
    public void newConection(WebSocketSession session) {
        UUID id = mapper.getUserIdFromURL(session);

        Optional<User> user = userRespository.findById(id);
        if (user.isEmpty()) {
            return;
        }
        this.usersonline.put(user.get().getId(), user.get());
        this.sessionService.put(user.get().getId(), session);
        this.eventService.sendEvent(new Event(null, id, user.get(), EventType.USER_DATA), session);
        System.out.println("conxao: " + user.get().getName());

    }

    @Override
    public void getAll(UUID id) {

        List<User> users = this.usersonline.values().stream().filter((user -> !user.getId().equals(id))).toList();
        sendEvent(id, new Event(null, id, users, EventType.GET_USERS_ONLINE));
    }

    @Override
    public void sendEvent(UUID id, Event event) {
        var session = sessionService.get(id);
        eventService.sendEvent(event, session);
    }

    @Override
    public void turnOffline(WebSocketSession session) {
        UUID id = mapper.getUserIdFromURL(session);
        usersonline.remove(id);
        sessionService.remove(id);

    }

    @Override
    public void turnOffline(UUID uuid) {
        turnOffline(sessionService.get(uuid));
    }


}
