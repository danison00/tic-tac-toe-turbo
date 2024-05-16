package com.dandev.tictactoeturbo.socket.infra.classes;

import com.dandev.tictactoeturbo.socket.infra.enums.ResponseStatusCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Response<T> {


    private UUID idSender;
    private UUID idReceiver;
    private ResponseStatusCode status;
    private String statusMessage;
    private T body;



    public static Builder<?> idReceiver( UUID idReceiver) {
        return new Builder<>(idReceiver);
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder<T> {

        private UUID idSender;
        private UUID idReceiver;
        private ResponseStatusCode status;
        private String statusMessage;
        private T body;

        private Builder( UUID idReceiver){
            this.idReceiver = idReceiver;
        }
        public Response<T> status(ResponseStatusCode status) {
            return  new Response<>(idSender, idReceiver, status, status.getMessage(), body);
        }
        public Builder<T> idSender(UUID idSender) {
            return  new Builder<>(idSender, idReceiver, status, statusMessage, body);
        }

        public <T> Builder<T> body(T body) {
            return new Builder<>(idSender, idReceiver, status, statusMessage, body );
        }


    }


}
