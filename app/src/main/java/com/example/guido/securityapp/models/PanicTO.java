package com.example.guido.securityapp.models;

import com.example.guido.securityapp.interfaces.IGetLocation;
import com.example.guido.securityapp.interfaces.IGetToken;

/**
 * Created by guido on 2/14/15.
 */
public class PanicTO implements IGetToken {
    private MyLocation location;
    private String token;
    private String groupId;
    private String message;

    public PanicTO() {
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public MyLocation getLocation() {
        return location;
    }

    public void setLocation(MyLocation location) {
        this.location = location;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
