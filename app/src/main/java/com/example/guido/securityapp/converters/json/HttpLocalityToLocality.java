package com.example.guido.securityapp.converters.json;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.models.LocalitiesFromServer;
import com.example.guido.securityapp.models.Locality;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by guido on 2/28/15.
 */
public class HttpLocalityToLocality extends Converter {

   private JsonToObject helperConverter;
   private HashMap<String,Locality.LocalityClassification> httpToClassificationLocality;

    public HttpLocalityToLocality() {
        helperConverter = new JsonToObject(Locality.class);
        initialize();
    }

    private void initialize()
    {
        httpToClassificationLocality = new HashMap<>();
        httpToClassificationLocality.put("set_secure_locality", Locality.LocalityClassification.SECURE);
        httpToClassificationLocality.put("set_insecure_locality", Locality.LocalityClassification.INSECURE);
        httpToClassificationLocality.put("set_unclassified_locality", Locality.LocalityClassification.UNCLASSIFIED);
    }

    @Override
    public Object convert(Object objectToConvert) throws Exception {
        try
        {
            String json = (String) objectToConvert;
            JSONObject jsonObject = new JSONObject(json);
            Locality locality = (Locality) helperConverter.convert(jsonObject.getJSONObject("locality").toString());
            Locality.LocalityClassification classification = toLocalityClassification(jsonObject.getString("type"));
            locality.setClassification(classification);

            return locality;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException("I was expecting a string but received "+objectToConvert.getClass());
        }
    }

    private Locality.LocalityClassification toLocalityClassification(String http)
    {
        return httpToClassificationLocality.get(http);
    }
}
