package com.example.guido.securityapp.services;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.example.guido.securityapp.asyncTasks.NotificationTask;
import com.example.guido.securityapp.builders.services.BuilderServiceGroup;
import com.example.guido.securityapp.builders.services.BuilderServiceUserToken;
import com.example.guido.securityapp.models.MyLocation;
import com.example.guido.securityapp.models.NotificationTO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by guido on 3/2/15.
 */
public class LocationNotifier implements LocationListener {

    @Override
    public void onLocationChanged(Location location) {
        try {
            NotificationTO notificationTO = makeNotificationTransferObject(location);
            NotificationTask task = new NotificationTask(notificationTO);
            task.execute((Void)null);
        } catch (Exception e) {
           //TODO HANDLE EXCEPTION
        }
    }

    private NotificationTO makeNotificationTransferObject(Location location) throws Exception
    {
        MyLocation myLocation = new MyLocation(location.getLatitude(),location.getLongitude());
        String token = BuilderServiceUserToken.build().getToken();
        String groupId = BuilderServiceGroup.buildGroupInformationService().getGroup().getId();

        NotificationTO notificationTO = new NotificationTO();
        notificationTO.setMessage(null);
        notificationTO.setToken(token);
        notificationTO.setGroupId(groupId);
        notificationTO.setLocation(myLocation);

        return notificationTO;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
