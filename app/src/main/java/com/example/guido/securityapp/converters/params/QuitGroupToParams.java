package com.example.guido.securityapp.converters.params;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.models.QuitGroupTO;

import org.json.JSONObject;

/**
 * Created by guido on 2/15/15.
 */
public class QuitGroupToParams extends Converter {
    @Override
    public Object convert(Object objectToConvert) throws Exception {
        try
        {
            QuitGroupTO maybeQuitGroupTO = (QuitGroupTO) objectToConvert;
            JSONObject quitGroupJson = new JSONObject();
            quitGroupJson.put("id",maybeQuitGroupTO.getGroupId());

            return quitGroupJson.toString();
        }
        catch (ClassCastException e)
        {
            throw new IllegalArgumentException("I was expecting "+QuitGroupTO.class.toString()+" but received "+objectToConvert.getClass().toString());
        }
    }
}
