package com.example.guido.securityapp.builders.services;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.json.DefaultConverter;
import com.example.guido.securityapp.services.ServicePanicMessage;
import com.example.guido.securityapp.services.ServiceStore;

/**
 * Created by guido on 2/22/15.
 */
public class BuilderServicePanicMessage {

    private static ServicePanicMessage servicePanicMessage = null;

    public static ServicePanicMessage build()
    {
        if(servicePanicMessage==null)
        {
            ServiceStore store = new ServiceStore(MyApplication.getContext().getResources().getString(R.string.panic_message_store_key),new DefaultConverter(), new DefaultConverter());
            servicePanicMessage = new ServicePanicMessage(store);
        }
        return servicePanicMessage;
    }

    public void setServicePanicMessage(ServicePanicMessage service)
    {
        servicePanicMessage = service;
    }

    public void clean()
    {
        servicePanicMessage = null;
    }
}
