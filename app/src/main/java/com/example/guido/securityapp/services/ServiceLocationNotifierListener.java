package com.example.guido.securityapp.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;

import com.example.guido.securityapp.activities.MyApplication;

/**
 * Created by guido on 3/2/15.
 */
public class ServiceLocationNotifierListener extends Service {
    private LocationManager locationManager;
    LocationNotifier networkListenerDistanceBased;
    LocationNotifier gpsListenerDistanceBased;
    LocationNotifier networkListenerTimeBased;
    LocationNotifier gpsListenerTimeBased;
    private long minTimeForNextLocation = 3600000; // 1 hour
    private float minDistanceForNextLocation = 5f; // metres


    public void startTheService()
    {
        locationManager = (LocationManager) MyApplication.getContext().getSystemService(Context.LOCATION_SERVICE);
        networkListenerDistanceBased = new LocationNotifier();
        gpsListenerDistanceBased = new LocationNotifier();
        networkListenerTimeBased = new LocationNotifier();
        gpsListenerTimeBased = new LocationNotifier();

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, minDistanceForNextLocation, networkListenerDistanceBased);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, minDistanceForNextLocation, gpsListenerDistanceBased);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTimeForNextLocation, 0, networkListenerTimeBased);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTimeForNextLocation, 0, gpsListenerTimeBased);

    }

    public void stopTheService()
    {
        stopListener(networkListenerDistanceBased);
        stopListener(gpsListenerDistanceBased);
        stopListener(networkListenerTimeBased);
        stopListener(gpsListenerTimeBased);

        networkListenerDistanceBased = null;
        gpsListenerDistanceBased = null;
        networkListenerTimeBased = null;
        gpsListenerTimeBased = null;
        locationManager = null;
    }

    protected void stopListener(LocationListener listener)
    {
        if (listener != null) {
            locationManager.removeUpdates(listener);
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTheService();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopTheService();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
