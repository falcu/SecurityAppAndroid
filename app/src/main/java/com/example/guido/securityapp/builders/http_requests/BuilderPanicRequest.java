package com.example.guido.securityapp.builders.http_requests;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.params.PanicToParams;
import com.example.guido.securityapp.models.PanicTO;

/**
 * Created by guido on 2/14/15.
 */
public class BuilderPanicRequest extends BuilderBaseRequestWithToken{
    @Override
    protected Class getSpecificClass() {
        return PanicTO.class;
    }

    @Override
    protected Converter getConverter() {
        return new PanicToParams();
    }

    @Override
    protected String getSpecificUri() {
        return "/api/notifications/send";
    }
}
