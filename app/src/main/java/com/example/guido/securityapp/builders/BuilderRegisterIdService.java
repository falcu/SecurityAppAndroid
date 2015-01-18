package com.example.guido.securityapp.builders;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.json.DefaultConverter;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.services.RegisterIdService;
import com.example.guido.securityapp.services.ServiceStore;

/**
 * Created by guido on 1/18/15.
 */
public class BuilderRegisterIdService {

    private static RegisterIdService service = null;

    public static RegisterIdService buildRegisterIdService()
    {
        if(service==null)
        {
            String identifier = MyApplication.getContext().getString(R.string.register_id_key);
            IDataStore store = new ServiceStore(identifier,new DefaultConverter(),new DefaultConverter());
            service = new RegisterIdService(store);
        }

        return service;
    }
}
