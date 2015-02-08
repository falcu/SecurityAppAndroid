package com.example.guido.securityapp.models;

/**
 * Created by guido on 1/17/15.
 */
public class SignedUser extends Member {

    private String id;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
