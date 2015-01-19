package com.example.guido.securityapp.converters.json;

import com.example.guido.securityapp.converters.Converter;

import org.json.JSONObject;

/**
 * Created by guido on 1/18/15.
 */
public class HttpUserResponseToJson extends Converter{
    @Override
    public Object convert(Object objectToConvert) throws Exception {

        try
        {
            String serverJson = (String) objectToConvert;
            JSONObject json = new JSONObject(serverJson);
            return json.getJSONObject("user").toString();
        }

        catch (ClassCastException e)
        {
            throw new IllegalArgumentException("I was expecting a String");
        }


    }
}
