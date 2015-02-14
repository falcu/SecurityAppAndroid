package com.example.guido.securityapp.builders.services;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.json.DefaultConverter;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.services.ServiceRegisterId;
import com.example.guido.securityapp.services.ServiceStore;

/**
 * Created by guido on 1/18/15.
 */
public class BuilderServiceRegisterId {

    private static ServiceRegisterId service = null;

    public static ServiceRegisterId buildRegisterIdService()
    {
        if(service==null)
        {
            String identifier = MyApplication.getContext().getString(R.string.register_id_store_key);
            IDataStore store = new ServiceStore(identifier,new DefaultConverter(),new DefaultConverter());
            service = new ServiceRegisterId(store);
        }

        return service;
    }
}
