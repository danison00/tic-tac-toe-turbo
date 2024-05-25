package com.dandev.tictactoeturbo.socket.infra.pattern;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractObservableUserManager<T> {

   public List<Subscriber<T>> observersSingleUser = new ArrayList<>();
   public List<Subscriber<List<T>>> observersListUser = new ArrayList<>();
    public void observerSingleUser(Subscriber<T> subscriber){

        observersSingleUser.add(subscriber);
    }
    public void observerUsersOnline(Subscriber<List<T>> subscriber){

        observersListUser.add(subscriber);
    }

    public void notifyObserversSingleUser(T t){
        this.observersSingleUser.forEach(subscriber -> subscriber.execute(t));
    };
    public void notifyObserversListUser(List<T> tList){
        this.observersListUser.forEach(subscriber -> subscriber.execute(tList));
    }

}
