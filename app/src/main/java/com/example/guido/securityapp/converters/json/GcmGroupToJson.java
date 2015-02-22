package com.example.guido.securityapp.converters.json;

import com.example.guido.securityapp.converters.Converter;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by guido on 2/21/15.
 */
public class GcmGroupToJson extends Converter {
    @Override
    public Object convert(Object objectToConvert) throws Exception {
        try
        {
            String gcmJson = (String) objectToConvert;
            JSONObject json = new JSONObject(gcmJson);
            //Application supports only one group
            JSONObject groupJson = json.getJSONObject("group");
            JSONArray members = json.getJSONArray("members");
            JSONObject creator = json.getJSONObject("creator");

            JSONObject modifiedJson = new JSONObject();
            modifiedJson.put("name",groupJson.get("name"));
            modifiedJson.put("id",groupJson.get("id"));
            modifiedJson.put("creatorId",groupJson.get("user_id"));
            modifiedJson.put("creator",creator);
            modifiedJson.put("members",members);

            return modifiedJson.toString();
        }

        catch (ClassCastException e)
        {
            throw new IllegalArgumentException("I was expecting a String");
        }
    }
}
