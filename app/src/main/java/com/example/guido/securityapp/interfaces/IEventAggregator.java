package com.example.guido.securityapp.interfaces;

/**
 * Created by guido on 2/17/15.
 */
public interface IEventAggregator {

    public void Subscribe(ISubscriber subscriber,String key);
    public void Publish(String key,Object dataToPublish);
}
