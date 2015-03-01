package com.example.guido.securityapp.helpers;

import com.example.guido.securityapp.models.Locality;

/**
* Created by guido on 3/1/15.
*/
public class MenuItemData
{
    private int groupId;
    private int order;
    private int id;
    private String title;
    private Locality.LocalityClassification classification;

    public MenuItemData(int groupId, int order, int id, String title, Locality.LocalityClassification classification) {
        this.groupId = groupId;
        this.order = order;
        this.id = id;
        this.title = title;
        this.classification = classification;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Locality.LocalityClassification getClassification() {
        return classification;
    }

    public void setClassification(Locality.LocalityClassification classification) {
        this.classification = classification;
    }
}
