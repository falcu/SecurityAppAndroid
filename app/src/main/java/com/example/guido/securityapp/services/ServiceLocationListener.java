package com.example.guido.securityapp.services;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.interfaces.IGetLocation;
import com.example.guido.securityapp.models.MyLocation;

/**
 * Created by guido on 2/14/15.
 */
public class ServiceLocationListener implements LocationListener{

    private Location lastLocation;

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
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

    public synchronized Location getLastLocation() {
        return lastLocation;
    }
}
