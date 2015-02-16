package com.example.guido.securityapp.models;

import com.example.guido.securityapp.interfaces.IGetToken;

/**
 * Created by guido on 2/15/15.
 */
public class QuitGroupTO implements IGetToken{

    private String token;
    private String groupId;

    public QuitGroupTO(String groupId,String token)
    {
        this.groupId = groupId;
        this.token = token;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
