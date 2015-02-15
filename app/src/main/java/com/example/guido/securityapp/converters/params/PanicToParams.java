package com.example.guido.securityapp.converters.params;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.models.NewMemberTO;
import com.example.guido.securityapp.models.PanicTO;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by guido on 2/14/15.
 */
public class PanicToParams extends Converter {
    @Override
    public Object convert(Object objectToConvert) throws Exception {
        try
        {
            PanicTO maybePanicTO = (PanicTO) objectToConvert;
            JSONObject paramsJson = new JSONObject();
            paramsJson.put("group_id",maybePanicTO.getGroupId());
            paramsJson.put("latitude",Double.toString(maybePanicTO.getLocation().getLatitude()));
            paramsJson.put("longitude",Double.toString(maybePanicTO.getLocation().getLongitude()));
            paramsJson.put("alarm",maybePanicTO.getMessage());

            return paramsJson.toString();

        }
        catch (ClassCastException e)
        {
            throw new IllegalArgumentException("I was expecting "+PanicTO.class.toString()+" but received "+objectToConvert.getClass().toString());
        }
    }
}
