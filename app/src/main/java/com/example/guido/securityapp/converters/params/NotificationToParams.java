package com.example.guido.securityapp.converters.params;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.models.NotificationTO;

import org.json.JSONObject;

/**
 * Created by guido on 3/2/15.
 */
public class NotificationToParams extends Converter {
    @Override
    public Object convert(Object objectToConvert) throws Exception {
        try
        {
            NotificationTO maybeNotificationTO = (NotificationTO) objectToConvert;
            JSONObject paramsJson = new JSONObject();
            paramsJson.put("group_id", maybeNotificationTO.getGroupId());
            paramsJson.put("latitude",Double.toString(maybeNotificationTO.getLocation().getLatitude()));
            paramsJson.put("longitude",Double.toString(maybeNotificationTO.getLocation().getLongitude()));
            if(maybeNotificationTO.getMessage()!=null && !maybeNotificationTO.getMessage().isEmpty())
                paramsJson.put("message", maybeNotificationTO.getMessage());

            return paramsJson.toString();

        }
        catch (ClassCastException e)
        {
            throw new IllegalArgumentException("I was expecting "+NotificationTO.class.toString()+" but received "+objectToConvert.getClass().toString());
        }
    }
}
