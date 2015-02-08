package com.example.guido.securityapp.converters.json;

import com.example.guido.securityapp.converters.Converter;

import org.json.JSONObject;

/**
 * Created by guido on 2/1/15.
 */
public class HttpCreateGroupResponseToJson extends Converter {
    @Override
    public Object convert(Object objectToConvert) throws Exception {
        try
        {
            String serverJson = (String) objectToConvert;
            JSONObject json = new JSONObject(serverJson);
            JSONObject groupJson = json.getJSONObject("group");
            JSONObject modifiedJson = new JSONObject();
            modifiedJson.put("name",groupJson.get("name"));
            modifiedJson.put("id",groupJson.get("id"));
            modifiedJson.put("creatorId",groupJson.get("user_id"));

            return modifiedJson.toString();
        }

        catch (ClassCastException e)
        {
            throw new IllegalArgumentException("I was expecting a String");
        }
    }
}
