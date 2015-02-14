package com.example.guido.securityapp.builders.http_requests;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.params.UserToSignInParams;

/**
 * Created by guido on 1/18/15.
 */
public class BuilderSignInUserRequest extends BuilderSignUpUserRequest {

    @Override
    protected String getSpecificUri() {
        return "/api/users/sign_in";
    }

    @Override
    protected Converter getConverter() {
        return new UserToSignInParams();
    }
}
