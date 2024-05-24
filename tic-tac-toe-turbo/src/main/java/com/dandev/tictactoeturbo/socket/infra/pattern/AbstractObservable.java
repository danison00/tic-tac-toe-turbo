package com.dandev.tictactoeturbo.socket.infra.pattern;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractObservable<T> {

   public List<Subscriber<T>> observers = new ArrayList<>();
    public void subscribe(Subscriber<T> subscriber){
        observers.add(subscriber);
    }

    public abstract void notifyObservers();
}
