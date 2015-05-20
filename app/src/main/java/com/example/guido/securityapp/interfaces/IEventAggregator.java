package com.example.guido.securityapp.interfaces;

/**
 * Created by guido on 2/17/15.
 */
public interface IEventAggregator {

    public void subscribe(ISubscriber subscriber, String key);
    public void publish(String key, Object dataToPublish);
}
