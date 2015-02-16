package com.example.guido.securityapp.builders.http_requests;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.params.QuitGroupToParams;
import com.example.guido.securityapp.models.QuitGroupTO;

/**
 * Created by guido on 2/16/15.
 */
public class BuilderQuitGroupRequest extends BuilderBaseRequestWithToken{
    @Override
    protected Class getSpecificClass() {
        return QuitGroupTO.class;
    }

    @Override
    protected Converter getConverter() {
        return new QuitGroupToParams();
    }

    @Override
    protected String getSpecificUri() {
        return "/api/groups/quit";
    }
}
