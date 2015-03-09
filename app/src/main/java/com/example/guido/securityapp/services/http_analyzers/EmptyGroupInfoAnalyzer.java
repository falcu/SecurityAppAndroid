package com.example.guido.securityapp.services.http_analyzers;

import com.example.guido.securityapp.interfaces.IMessageAnalyzer;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by guido on 3/8/15.
 */
public class EmptyGroupInfoAnalyzer implements IMessageAnalyzer{
    private boolean didLastDataHaveError;
    private String lastError = null;

    @Override
    public void analyze(Object data) {
        try
        {
            String serverJson = (String) data;
            JSONObject json = new JSONObject(serverJson);
            JSONArray groupJsonArray = json.getJSONArray("group_info");
            if(groupJsonArray.length()==0)
            {
                didLastDataHaveError = true;
                lastError = "You don't belong to a group";
            }
            else
            {
                didLastDataHaveError = false;
            }
        }
        catch (Exception e)
        {
            didLastDataHaveError = true;
            lastError = e.getMessage();
        }

    }

    @Override
    public boolean didLastDataHaveMessage() {
        return didLastDataHaveError;
    }

    @Override
    public String getLastMessage() {
        return lastError;
    }
}
