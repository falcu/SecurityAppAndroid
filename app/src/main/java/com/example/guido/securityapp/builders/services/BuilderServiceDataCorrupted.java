package com.example.guido.securityapp.builders.services;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.json.DefaultConverter;
import com.example.guido.securityapp.converters.json.JsonToObject;
import com.example.guido.securityapp.models.Group;
import com.example.guido.securityapp.services.ServiceDataCorrupted;
import com.example.guido.securityapp.services.ServiceStore;

/**
 * Created by guido on 2/17/15.
 */
public class BuilderServiceDataCorrupted {

    private static ServiceDataCorrupted service = null;

    public static ServiceDataCorrupted build()
    {
        if(service==null)
        {
            service = new ServiceDataCorrupted();
            service.addLoader(new ServiceStore(MyApplication.getContext().getString(R.string.group_store_key), new JsonToObject(Group.class),new DefaultConverter()));
            service.addLoader(new ServiceStore(MyApplication.getContext().getString(R.string.signed_user_store_key), new JsonToObject(Group.class),new DefaultConverter()));
        }
        return service;
    }
}
