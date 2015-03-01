package com.example.guido.securityapp.converters.json;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.models.LocalitiesFromServer;
import com.example.guido.securityapp.models.Locality;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guido on 2/23/15.
 */
public class HttpLocalitiesToLocalitiesList extends Converter {

    protected JsonToObject helperConverter;

    public HttpLocalitiesToLocalitiesList() {
        helperConverter = new JsonToObject(LocalitiesFromServer.class);
    }

    @Override
    public Object convert(Object objectToConvert) throws Exception {
        try
        {
            String json = (String) objectToConvert;
            JSONObject jsonObject = new JSONObject(json);
            LocalitiesFromServer localitiesFromServer = (LocalitiesFromServer) helperConverter.convert(jsonObject.getJSONObject("localities_info").toString());
            List<Locality> localities = new ArrayList<>();
            for(Locality l: localitiesFromServer.getUnclassified())
            {
                localities.add(l);
            }

            for(Locality l: localitiesFromServer.getSecure())
            {
                l.setClassification(Locality.LocalityClassification.SECURE);
                localities.add(l);
            }

            for(Locality l: localitiesFromServer.getInsecure())
            {
                l.setClassification(Locality.LocalityClassification.INSECURE);
                localities.add(l);
            }

            return localities;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException("I was expecting a string but received "+objectToConvert.getClass());
        }
    }
}
