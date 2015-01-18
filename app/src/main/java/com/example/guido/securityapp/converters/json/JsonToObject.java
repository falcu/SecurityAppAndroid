package com.example.guido.securityapp.converters.json;

import com.example.guido.securityapp.converters.Converter;
import com.google.gson.Gson;

/**
 * Created by guido on 1/17/15.
 */
public class JsonToObject extends Converter {

    private Class objectClass;

    public JsonToObject(Class objectClass)
    {
        this.objectClass = objectClass;
    }

    @Override
    public Object convert(Object json) {
        Gson gson = new Gson();
        Object obj = gson.fromJson((String)json, objectClass);
        return obj;
    }
}
