package com.example.guido.securityapp.converters.json;

import com.example.guido.securityapp.converters.Converter;
import com.google.gson.Gson;

/**
 * Created by guido on 1/17/15.
 */
public class ObjectToJson extends Converter {

    @Override
    public String convert(Object object) {
        Gson gson = new Gson();
        String stringJson = gson.toJson(object);
        return stringJson;
    }
}
