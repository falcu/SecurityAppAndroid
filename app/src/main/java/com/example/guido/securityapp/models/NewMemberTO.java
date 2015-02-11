package com.example.guido.securityapp.models;

import com.example.guido.securityapp.interfaces.IGetToken;

/**
 * Created by guido on 2/8/15.
 */
public class NewMemberTO implements IGetToken {
    private String email;
    private String groupId;
    private String token;

    public NewMemberTO(String email,String groupId,String token)
    {
        this.email = email;
        this.groupId = groupId;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
