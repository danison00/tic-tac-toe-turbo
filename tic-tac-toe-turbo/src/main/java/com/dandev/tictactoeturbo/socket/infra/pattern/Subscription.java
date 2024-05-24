package com.dandev.tictactoeturbo.socket.infra.pattern;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class Subscription {

    private Observable<?> observable;
    private UUID id;

    public void unsubscribe(){
       this.observable.unsubscribe(id);
    }
}
