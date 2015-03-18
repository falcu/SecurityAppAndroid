package com.example.guido.securityapp.builders.services;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.services.ServiceDeleteData;
import com.example.guido.securityapp.services.ServiceDeleteAll;

/**
 * Created by guido on 2/17/15.
 */
public class BuilderServiceDeleteAll {

    private static ServiceDeleteAll serviceSignOut = null;
    private static ServiceDeleteAll serviceQuitGroup = null;
    private static ServiceDeleteAll serviceDeleteFromGroup = null;

    public static ServiceDeleteAll buildSignOut()
    {
        if(serviceSignOut ==null)
        {
            ServiceDeleteData deleteUserService = new ServiceDeleteData(MyApplication.getContext().getResources().getString(R.string.signed_user_store_key));
            ServiceDeleteData deleteGroup = new ServiceDeleteData(MyApplication.getContext().getResources().getString(R.string.group_store_key));
            ServiceDeleteData deleteAlarms = new ServiceDeleteData(MyApplication.getContext().getResources().getString(R.string.alarms_store_key));
            ServiceDeleteData deletePanicMessage = new ServiceDeleteData(MyApplication.getContext().getResources().getString(R.string.panic_message_store_key));
            ServiceDeleteData deleteLocalities = new ServiceDeleteData(MyApplication.getContext().getResources().getString(R.string.localities_store_key));
            ServiceDeleteData deleteNotifications = new ServiceDeleteData(MyApplication.getContext().getResources().getString(R.string.locality_notifications_store_key));
            serviceSignOut = new ServiceDeleteAll();
            serviceSignOut.addRemover(deleteUserService);
            serviceSignOut.addRemover(deleteGroup);
            serviceSignOut.addRemover(deleteAlarms);
            serviceSignOut.addRemover(deletePanicMessage);
            serviceSignOut.addRemover(deleteLocalities);
            serviceSignOut.addRemover(deleteNotifications);
        }

        return serviceSignOut;
    }

    public static ServiceDeleteAll buildQuitGroup()
    {
        if(serviceQuitGroup==null)
        {
            ServiceDeleteData deleteAlarms = new ServiceDeleteData(MyApplication.getContext().getResources().getString(R.string.alarms_store_key));
            ServiceDeleteData deleteNotifications = new ServiceDeleteData(MyApplication.getContext().getResources().getString(R.string.locality_notifications_store_key));
            serviceQuitGroup = new ServiceDeleteAll();
            serviceQuitGroup.addRemover(deleteAlarms);
            serviceQuitGroup.addRemover(deleteNotifications);
        }
        return serviceQuitGroup;
    }

    public static ServiceDeleteAll buildDeletedFromGroup()
    {
        if(serviceDeleteFromGroup==null)
        {
            ServiceDeleteData deleteAlarms = new ServiceDeleteData(MyApplication.getContext().getResources().getString(R.string.alarms_store_key));
            ServiceDeleteData deleteNotifications = new ServiceDeleteData(MyApplication.getContext().getResources().getString(R.string.locality_notifications_store_key));
            ServiceDeleteData deleteGroup = new ServiceDeleteData(MyApplication.getContext().getResources().getString(R.string.group_store_key));
            serviceDeleteFromGroup = new ServiceDeleteAll();
            serviceDeleteFromGroup.addRemover(deleteAlarms);
            serviceDeleteFromGroup.addRemover(deleteNotifications);
            serviceDeleteFromGroup.addRemover(deleteGroup);
        }
        return serviceDeleteFromGroup;
    }

    public static void setServiceSignOut(ServiceDeleteAll service)
    {
       serviceSignOut = service;
    }

    public static void setServiceQuitGroup(ServiceDeleteAll service)
    {
        serviceQuitGroup = service;
    }

    public static void setServiceDeletedFromGroup(ServiceDeleteAll service){serviceDeleteFromGroup = service;}

    public static void clean()
    {
        serviceSignOut = null;
        serviceQuitGroup = null;
        serviceDeleteFromGroup = null;
    }

}
