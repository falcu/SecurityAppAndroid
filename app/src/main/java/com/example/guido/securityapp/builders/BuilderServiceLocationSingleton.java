package com.example.guido.securityapp.builders;

import com.example.guido.securityapp.services.ServiceLocation;

/**
 * Created by guido on 2/14/15.
 */
public class BuilderServiceLocationSingleton {

    public static ServiceLocation serviceLocation = null;

    public static ServiceLocation getServiceLocation()
    {
        if(serviceLocation==null)
        {
            serviceLocation = new ServiceLocation();
        }
        return serviceLocation;
    }

    public static void setServiceLocation(ServiceLocation service)
    {
        serviceLocation = service;
    }
}
