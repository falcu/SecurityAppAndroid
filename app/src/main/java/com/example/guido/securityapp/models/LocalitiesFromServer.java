package com.example.guido.securityapp.models;

import java.util.List;

/**
 * Created by guido on 2/23/15.
 */
public class LocalitiesFromServer {

    private List<Locality> unclassified;
    private List<Locality> secure;
    private List<Locality> insecure;

    public List<Locality> getUnclassified() {
        return unclassified;
    }

    public void setUnclassified(List<Locality> unclassified) {
        this.unclassified = unclassified;
    }

    public List<Locality> getSecure() {
        return secure;
    }

    public void setSecure(List<Locality> secure) {
        this.secure = secure;
    }

    public List<Locality> getInsecure() {
        return insecure;
    }

    public void setInsecure(List<Locality> insecure) {
        this.insecure = insecure;
    }
}
