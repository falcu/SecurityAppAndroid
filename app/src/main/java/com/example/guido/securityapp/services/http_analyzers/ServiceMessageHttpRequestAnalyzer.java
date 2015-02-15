package com.example.guido.securityapp.services.http_analyzers;

import com.example.guido.securityapp.interfaces.IMessageAnalyzer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by guido on 2/15/15.
 */
public class ServiceMessageHttpRequestAnalyzer implements IMessageAnalyzer {

    private boolean didLastDataHaveMessage;
    private String lastMessage = null;

    @Override
    public void analyze(Object data) {
        if(data.getClass()!=String.class)
            throw new IllegalArgumentException("I was expecting a String");

        try {
            JSONObject json = new JSONObject((String)data);
            reset();
            if(json.has("message"))
            {
                didLastDataHaveMessage = true;
                lastMessage = json.getString("message");
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Bad string format: it's not a json");
        }
    }

    @Override
    public boolean didLastDataHaveMessage() {
        return didLastDataHaveMessage;
    }

    @Override
    public String getLastMessage() {
        return lastMessage;
    }

    private void reset()
    {
        didLastDataHaveMessage = false;
        lastMessage = "";
    }
}
