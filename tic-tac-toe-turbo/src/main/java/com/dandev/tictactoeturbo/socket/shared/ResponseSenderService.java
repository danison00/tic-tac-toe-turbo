package com.dandev.tictactoeturbo.socket.shared;

import com.dandev.tictactoeturbo.socket.infra.classes.Response;
import com.dandev.tictactoeturbo.util.JsonConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.util.List;
import java.util.Optional;

import static com.dandev.tictactoeturbo.socket.shared.UserOnlineManager.UserOnline;

@Service
public class ResponseSenderService {

    private final JsonConverter jsonConverter;
    private final UserOnlineManager userOnlineManager;

    ResponseSenderService(JsonConverter jsonConverter, UserOnlineManager userOnlineManager) {
        this.jsonConverter = jsonConverter;
        this.userOnlineManager = userOnlineManager;
    }

    private void sendResponse(Response<?> response) {

        Optional<UserOnline> userOnlineOpt = userOnlineManager.getById(response.getIdReceiver());
        if (userOnlineOpt.isEmpty()) return;

        String eventSerialized = jsonConverter.serialize(response);
        try {
            if (userOnlineOpt.get().session().isOpen())
                userOnlineOpt.get().session().sendMessage(new TextMessage(eventSerialized));
            System.out.println("Event sent -> " + response);
        } catch (IOException  e) {
            System.out.println("Exception "+e.getCause()+"(ResponseSenderService.java:36)");
        }
    }

    public void send(Object response) {
        if (response instanceof List) {
            ((List<Response<?>>) response).forEach(this::sendResponse);
        } else if (response instanceof Response<?>) {
            sendResponse((Response<?>) response);
        } else {
            throw new IllegalArgumentException("Object " + response.getClass() + " is not of type class List<Response>" + " or " + Response.class);
        }
    }

}
