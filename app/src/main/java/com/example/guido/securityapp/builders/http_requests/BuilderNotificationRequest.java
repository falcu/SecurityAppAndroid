package com.example.guido.securityapp.builders.http_requests;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.params.NotificationToParams;
import com.example.guido.securityapp.converters.params.PanicToParams;
import com.example.guido.securityapp.models.NotificationTO;

/**
 * Created by guido on 3/2/15.
 */
public class BuilderNotificationRequest extends BuilderBaseRequestWithToken {

    @Override
    protected Class getSpecificClass() {
        return NotificationTO.class;
    }

    @Override
    protected Converter getConverter() {
        return new NotificationToParams();
    }

    @Override
    protected String getSpecificUri() {
        return "/api/localities/notify";
    }
}
