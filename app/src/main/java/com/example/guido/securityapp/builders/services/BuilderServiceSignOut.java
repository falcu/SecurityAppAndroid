package com.example.guido.securityapp.builders.services;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.services.ServiceDeleteData;
import com.example.guido.securityapp.services.ServiceSignOut;

/**
 * Created by guido on 2/17/15.
 */
public class BuilderServiceSignOut {

    private static ServiceSignOut serviceSignOut = null;

    public static ServiceSignOut build()
    {
        if(serviceSignOut==null)
        {
            ServiceDeleteData deleteUserService = new ServiceDeleteData(MyApplication.getContext().getResources().getString(R.string.signed_user_store_key));
            ServiceDeleteData deleteGroup = new ServiceDeleteData(MyApplication.getContext().getResources().getString(R.string.group_store_key));
            ServiceDeleteData deleteAlarms = new ServiceDeleteData(MyApplication.getContext().getResources().getString(R.string.alarms_store_key));
            ServiceDeleteData deletePanicMessage = new ServiceDeleteData(MyApplication.getContext().getResources().getString(R.string.panic_message_store_key));
            serviceSignOut = new ServiceSignOut();
            serviceSignOut.addRemover(deleteUserService);
            serviceSignOut.addRemover(deleteGroup);
            serviceSignOut.addRemover(deleteAlarms);
            serviceSignOut.addRemover(deletePanicMessage);
        }

        return serviceSignOut;
    }

    public static void setService(ServiceSignOut service)
    {
       serviceSignOut = service;
    }

    public static void clean()
    {
        serviceSignOut = null;
    }

}
