package com.example.guido.securityapp.converters.params;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.models.CreateGroupTO;
import com.example.guido.securityapp.models.RenameGroupTO;

import org.json.JSONObject;

/**
 * Created by guido on 2/15/15.
 */
public class RenameGroupToParams extends Converter {
    @Override
    public Object convert(Object objectToConvert) throws Exception {
        try
        {
            RenameGroupTO maybeRenameGroup = (RenameGroupTO) objectToConvert;
            JSONObject paramsJson = new JSONObject();
            paramsJson.put("name",maybeRenameGroup.getNewGroupName());
            paramsJson.put("id",maybeRenameGroup.getGroupId());

            return paramsJson.toString();
        }
        catch (ClassCastException e)
        {
            throw new IllegalArgumentException("I was expecting "+RenameGroupTO.class.toString()+" but received "+objectToConvert.getClass().toString());
        }
    }
}
