package com.example.guido.securityapp.models;

import com.example.guido.securityapp.interfaces.IGetToken;

/**
 * Created by guido on 2/15/15.
 */
public class RenameGroupTO implements IGetToken{
    private String token;
    private String newGroupName;
    private String groupId;

    public RenameGroupTO(String token, String newGroupName, String groupId) {
        this.token = token;
        this.newGroupName = newGroupName;
        this.groupId = groupId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewGroupName() {
        return newGroupName;
    }

    public void setNewGroupName(String newGroupName) {
        this.newGroupName = newGroupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
