package com.example.guido.securityapp.builders;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.params.UserToSignInParams;

/**
 * Created by guido on 1/18/15.
 */
public class BuilderSignInUserRequest extends BuilderSignUpUserRequest{

    @Override
    protected String getFullUri()
    {
        return serverUri + "/api/users/sign_in";
    }

    @Override
    protected Converter getConverter() {
        return new UserToSignInParams();
    }
}
