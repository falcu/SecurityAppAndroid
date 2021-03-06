package com.example.guido.securityapp.services.http_analyzers;

import com.example.guido.securityapp.interfaces.IMessageAnalyzer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by guido on 1/25/15.
 */
public class ServiceBadHttpRequestAnalyzer implements IMessageAnalyzer {

    private boolean didLastDataHaveError;
    private String lastError = null;
    @Override
    public void analyze(Object data) {
        if(data.getClass()!=String.class)
            throw new IllegalArgumentException("I was expecting a String");

        try {
            JSONObject json = new JSONObject((String)data);
            reset();
            if(json.has("error"))
            {
                didLastDataHaveError = true;
                lastError = json.getString("error");
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Bad string format: it's not a json");
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

    private void reset()
    {
        didLastDataHaveError = false;
        lastError = "";
    }
}
