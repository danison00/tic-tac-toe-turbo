package com.dandev.tictactoeturbo.socket.infra.pattern;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Observable<T> {

    private final Map<UUID, Subscriber<T>> observers = new ConcurrentHashMap<>();

    public Subscription subscribe(Subscriber<T> subscriber){
        UUID id = UUID.randomUUID();
        this.observers.put(id, subscriber);
        return  new Subscription(this, id);
    }
    public void next(T t){
        observers.forEach((uuid, tSubscriber) -> {
            tSubscriber.execute(t);
        });
    }
    protected void unsubscribe(UUID id){
        this.observers.remove(id);
    }


}
