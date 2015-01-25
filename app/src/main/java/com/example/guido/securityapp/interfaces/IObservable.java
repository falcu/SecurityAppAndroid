package com.example.guido.securityapp.interfaces;

import java.util.Observer;

/**
 * Created by guido on 1/24/15.
 */
public interface IObservable {
    public void addObserver(Observer observer);
    public void deleteObserver(Observer observer);
    public void deleteObservers();
    public void notifyObservers();
    public void notifyObservers(Object data);
}
