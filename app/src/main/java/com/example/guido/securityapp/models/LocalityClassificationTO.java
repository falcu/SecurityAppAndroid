package com.example.guido.securityapp.models;

import com.example.guido.securityapp.interfaces.IGetToken;

/**
 * Created by guido on 2/28/15.
 */
public class LocalityClassificationTO implements IGetToken {
    private int id;
    private Locality.LocalityClassification classification;
    private String token;

    public LocalityClassificationTO(int id, Locality.LocalityClassification classification, String token) {
        this.id = id;
        this.classification = classification;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Locality.LocalityClassification getClassification() {
        return classification;
    }

    public void setClassification(Locality.LocalityClassification classification) {
        this.classification = classification;
    }
}
