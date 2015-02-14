package com.example.guido.securityapp.builders.http_requests;

import com.example.guido.securityapp.builders.http_requests.BuilderBaseRequest;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.params.UserToSignUpParams;
import com.example.guido.securityapp.models.UserTO;

/**
 * Created by guido on 1/17/15.
 */
public class BuilderSignUpUserRequest extends BuilderBaseRequest {

    public BuilderSignUpUserRequest()
    {
        super();
    }

    @Override
    protected String getSpecificUri() {
        return "/api/users/create";
    }

    @Override
    protected Converter getConverter()
    {
        return new UserToSignUpParams();
    }

    @Override
    protected Class getSpecificClass() {
        return UserTO.class;
    }
}
