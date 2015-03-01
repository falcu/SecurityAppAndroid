package com.example.guido.securityapp.converters.params;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.models.LocalityClassificationTO;
import com.example.guido.securityapp.models.RenameGroupTO;

import org.json.JSONObject;

/**
 * Created by guido on 2/28/15.
 */
public class SetLocalityClassificationToParams extends Converter {
    @Override
    public Object convert(Object objectToConvert) throws Exception {
        try
        {
            LocalityClassificationTO maybeLocalityClassification = (LocalityClassificationTO) objectToConvert;
            JSONObject paramsJson = new JSONObject();
            paramsJson.put("id",maybeLocalityClassification.getId());
            paramsJson.put("locality_classification",maybeLocalityClassification.getClassification().toString().toLowerCase());

            return paramsJson.toString();
        }
        catch (ClassCastException e)
        {
            throw new IllegalArgumentException("I was expecting "+LocalityClassificationTO.class.toString()+" but received "+objectToConvert.getClass().toString());
        }
    }
}
