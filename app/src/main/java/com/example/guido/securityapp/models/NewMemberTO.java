package com.example.guido.securityapp.models;

import com.example.guido.securityapp.interfaces.IGetToken;

/**
 * Created by guido on 2/8/15.
 */
public class NewMemberTO implements IGetToken {
    private String email;
    private int groupId;
    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
