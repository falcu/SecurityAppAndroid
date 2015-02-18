package com.example.guido.securityapp.helpers;

import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.interfaces.ISubscriber;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by guido on 2/17/15.
 */
public class SimpleEventAggregator implements IEventAggregator{

    private HashMap<String,List<WeakReference<ISubscriber>>> subscribers;
    private Object lock;

    public SimpleEventAggregator()
    {
        subscribers = new HashMap<>();
        lock = new Object();
    }

    @Override
    public void Subscribe(ISubscriber subscriber, String key) {
        addSubscriber(subscriber,key);

    }

    @Override
    public void Publish(String key, Object dataToPublish) {

        synchronized (lock)
        {
            if(subscribers.containsKey(key))
            {
                List<WeakReference<ISubscriber>> subscriberToNotify = subscribers.get(key);
                for(WeakReference<ISubscriber> s : subscriberToNotify)
                {
                    (s.get()).onEvent(dataToPublish);
                }
            }
        }

    }

    private synchronized void addSubscriber(ISubscriber subscriber, String key)
    {
        if(subscribers.containsKey(key))
        {
            List<WeakReference<ISubscriber>> subscribersForKey = subscribers.get(key);
            subscribersForKey.add(new WeakReference(subscriber));
        }
        else
        {
            List<WeakReference<ISubscriber>> subscribersForKey = new ArrayList<>();
            subscribersForKey.add(new WeakReference(subscriber));
            subscribers.put(key,subscribersForKey);
        }
    }
}
