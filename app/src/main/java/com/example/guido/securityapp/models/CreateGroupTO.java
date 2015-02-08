package com.example.guido.securityapp.models;

import com.example.guido.securityapp.interfaces.IGetToken;

/**
 * Created by guido on 2/1/15.
 */
public class CreateGroupTO implements IGetToken{

    private String name;
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
