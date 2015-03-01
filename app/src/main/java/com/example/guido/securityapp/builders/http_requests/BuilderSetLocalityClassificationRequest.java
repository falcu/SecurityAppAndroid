package com.example.guido.securityapp.builders.http_requests;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.params.SetLocalityClassificationToParams;
import com.example.guido.securityapp.models.LocalityClassificationTO;

/**
 * Created by guido on 2/28/15.
 */
public class BuilderSetLocalityClassificationRequest extends BuilderBaseRequestWithToken{
    @Override
    protected Class getSpecificClass() {
        return LocalityClassificationTO.class;
    }

    @Override
    protected Converter getConverter() {
        return new SetLocalityClassificationToParams();
    }

    @Override
    protected String getSpecificUri() {
       return "/api/localities/set_locality_classification";
    }
}
