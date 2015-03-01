package com.example.guido.securityapp.models;

/**
 * Created by guido on 2/28/15.
 */
public class LocalityModel {

    private Locality locality;
    private boolean updating;

    public LocalityModel(Locality locality, boolean updating) {
        this.setLocality(locality);
        this.setUpdating(updating);
    }


    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    public boolean isUpdating() {
        return updating;
    }

    public void setUpdating(boolean updating) {
        this.updating = updating;
    }
}
