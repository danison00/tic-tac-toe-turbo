package com.dandev.tictactoeturbo.socket.infra.reflection;

import com.dandev.tictactoeturbo.infra.exceptions.GameNotFound;
import com.dandev.tictactoeturbo.socket.dtos.ErrorMessage;
import com.dandev.tictactoeturbo.socket.dtos.Request;
import com.dandev.tictactoeturbo.socket.enums.ErrorCode;
import com.dandev.tictactoeturbo.socket.infra.classes.Response;
import com.dandev.tictactoeturbo.socket.infra.enums.ResponseStatusCode;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.HandlerException;
import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.WebSocketControllerHandlerException;

import java.util.UUID;

@WebSocketControllerHandlerException
public class ControllerHandlerAdvice {

    @HandlerException(GameNotFound.class)
    public Response<ErrorMessage> gameNotFound(GameNotFound e, Request<?> request) {

        UUID id = UUID.fromString(
                request.uri()
                        .getParam("userId")
                        .orElseThrow(
                                () -> new RuntimeException("Id nao encontrado como parametro da uri " + request.uri())
                        ));

        ErrorMessage errorMessage = new ErrorMessage(ErrorCode.GAME_NOT_FOUND, "O jogo buscado nao foi encontrado!");
        return Response.idReceiver(id).body(errorMessage).status(ResponseStatusCode.ERROR);
    }
}
