package com.example.guido.securityapp.services;

import android.annotation.TargetApi;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.SystemClock;

import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.exceptions.NoAvailableLocation;
import com.example.guido.securityapp.exceptions.ServiceLocationNotStarted;
import com.example.guido.securityapp.interfaces.IGetLocation;
import com.example.guido.securityapp.models.MyLocation;

/**
 * Created by guido on 2/14/15.
 */
public class ServiceLocation implements IGetLocation {
    private LocationManager locationManager;
    MyLocationListener networkListener;
    MyLocationListener gpsListener;
    private long tolerateOldTime = 20000;
    private float tolerateAccuracy = 150;


    public ServiceLocation()
    {


    }

    public void startService()
    {
        locationManager = (LocationManager) MyApplication.getContext().getSystemService(Context.LOCATION_SERVICE);
        networkListener = new MyLocationListener();
        gpsListener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, networkListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsListener);
    }

    public void stopService()
    {
        if (networkListener != null) {
            locationManager.removeUpdates(networkListener);
            networkListener = null;
        }
        if (gpsListener != null) {
            locationManager.removeUpdates(gpsListener);
            gpsListener = null;
        }
        locationManager = null;
    }

    public boolean isLocationServiceEnabled() throws Exception
    {
        checkIfServiceWasStarted();
        return(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }


    @Override
    public MyLocation getLocation() throws NoAvailableLocation{


        Location gpsLocation = gpsListener.getLastLocation();
        Location networkLocation = networkListener.getLastLocation();
        if(gpsLocation==null)
        {
            if(networkLocation!=null)
            {
                return locationToMyLocation(networkLocation);
            }
            else
            {
                throw new NoAvailableLocation("It is not possible to get a location");
            }
        }
        else if(networkLocation==null)
        {
            return locationToMyLocation(gpsLocation);
        }
        else
        {
            return findBestLocation(gpsLocation,networkLocation);
        }
    }

    private void checkIfServiceWasStarted() throws Exception
    {
        if(locationManager==null)
        {
            throw new ServiceLocationNotStarted("You need to start the service first");
        }
    }

    private long locationAge(Location location) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            return locationAgePost17(location);
        return locationAgePre17(location);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private long locationAgePost17(Location location) {
        return (SystemClock.elapsedRealtimeNanos() - location.getElapsedRealtimeNanos()) / 1000000;
    }


    private long locationAgePre17(Location location) {
        return System.currentTimeMillis() - location.getTime();
    }

    private MyLocation locationToMyLocation(Location androidLocation)
    {
        return new MyLocation(androidLocation.getLatitude(),androidLocation.getLongitude(),locationAge(androidLocation));
    }

    private MyLocation findBestLocation(Location gpsLocation,Location networkLocation)
    {
        long ageGps = locationAge(gpsLocation);
        long ageNetwork = locationAge(networkLocation);

        if(ageGps<ageNetwork)
        {
            return locationToMyLocation(gpsLocation);
        }
        else if(ageGps<=tolerateOldTime)
        {
            return locationToMyLocation(gpsLocation);
        }
        else if(networkLocation.getAccuracy() > tolerateAccuracy)
        {
            return locationToMyLocation(gpsLocation);
        }
        else
        {
            return locationToMyLocation(networkLocation);
        }
    }
}
