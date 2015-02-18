package com.example.guido.securityapp.factories;

import com.example.guido.securityapp.helpers.SimpleEventAggregator;
import com.example.guido.securityapp.interfaces.IEventAggregator;

/**
 * Created by guido on 2/17/15.
 */
public class FactoryEventAggregator {

    private static IEventAggregator eventAggregator = null;

    public static IEventAggregator getInstance()
    {
        if(eventAggregator==null)
        {
            eventAggregator = new SimpleEventAggregator();
        }
        return eventAggregator;
    }

    public static void setEventAggregator(IEventAggregator aggregator)
    {
        eventAggregator = aggregator;
    }

    public void clean()
    {
        eventAggregator = null;
    }
}
