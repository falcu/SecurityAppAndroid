package com.example.guido.securityapp.models;

/**
 * Created by guido on 2/23/15.
 */
public class Locality {

    private int id;
    private String name;
    private LocalityClassification classification = LocalityClassification.UNCLASSIFIED;

    public Locality() {
    }

    public Locality(int id, String name, LocalityClassification classification) {
        this.setId(id);
        this.setName(name);
        this.setClassification(classification);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalityClassification getClassification() {
        return classification;
    }

    public void setClassification(LocalityClassification classification) {
        this.classification = classification;
    }

    public enum LocalityClassification
    {
        UNCLASSIFIED,SECURE,INSECURE
    }
}


