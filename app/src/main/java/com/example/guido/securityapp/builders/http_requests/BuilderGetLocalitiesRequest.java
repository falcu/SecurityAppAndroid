package com.example.guido.securityapp.builders.http_requests;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.json.StringEmptyConverter;
import com.example.guido.securityapp.models.TokenTO;

/**
 * Created by guido on 2/23/15.
 */
public class BuilderGetLocalitiesRequest extends BuilderBaseRequestWithToken {
    @Override
    protected Class getSpecificClass() {
        return TokenTO.class;
    }

    @Override
    protected Converter getConverter() {
        return new StringEmptyConverter();
    }

    @Override
    protected String getSpecificUri() {
        return "/api/localities/get_classified_localities?";
    }
}
