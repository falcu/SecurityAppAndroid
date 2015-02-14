package com.example.guido.securityapp.models;

/**
 * Created by guido on 2/14/15.
 */
public class MyLocation {

    private double latitude;
    private double longitude;
    private long timeOld; //ms

    public MyLocation(double latitude,double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public MyLocation(double latitude, double longitude,long timeOld){
        this(latitude,longitude);
        this.timeOld = timeOld;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTimeOld() {
        return timeOld;
    }

    public void setTimeOld(long timeOld) {
        this.timeOld = timeOld;
    }
}
