package com.example.guido.securityapp.converters.json;

import com.example.guido.securityapp.converters.Converter;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by guido on 2/5/15.
 */
public class HttpGroupInfoResponseToJson extends Converter {
    @Override
    public Object convert(Object objectToConvert) throws Exception {
        try
        {
            String serverJson = (String) objectToConvert;
            JSONObject json = new JSONObject(serverJson);
            JSONArray groupJsonArray = json.getJSONArray("group_info");
            //Application supports only one group
            JSONObject groupJson = groupJsonArray.getJSONObject(0).getJSONObject("group");
            JSONArray members = groupJsonArray.getJSONObject(0).getJSONArray("members");

            JSONObject modifiedJson = new JSONObject();
            modifiedJson.put("name",groupJson.get("name"));
            modifiedJson.put("id",groupJson.get("id"));
            modifiedJson.put("creatorId",groupJson.get("user_id"));
            modifiedJson.put("members",members);

            return modifiedJson.toString();
        }

        catch (ClassCastException e)
        {
            throw new IllegalArgumentException("I was expecting a String");
        }
    }
}
