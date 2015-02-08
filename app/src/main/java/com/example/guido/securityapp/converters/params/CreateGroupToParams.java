package com.example.guido.securityapp.converters.params;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.models.CreateGroupTO;

import org.json.JSONObject;

/**
 * Created by guido on 2/1/15.
 */
public class CreateGroupToParams extends Converter {
    @Override
    public Object convert(Object objectToConvert) throws Exception {
        try
        {
            CreateGroupTO maybeGroup = (CreateGroupTO) objectToConvert;
            JSONObject group = new JSONObject();
            JSONObject group_details = new JSONObject();
            group_details.put("name",maybeGroup.getName());
            group.put("group",group_details);
            return group.toString();
        }
        catch (ClassCastException e)
        {
            throw new IllegalArgumentException("I was expecting "+CreateGroupTO.class.toString()+" but received "+objectToConvert.getClass().toString());
        }
    }
}
