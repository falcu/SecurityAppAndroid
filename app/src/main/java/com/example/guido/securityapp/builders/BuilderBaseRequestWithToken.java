package com.example.guido.securityapp.builders;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.interfaces.IGetToken;
import com.example.guido.securityapp.restful.RequestPackage;

/**
 * Created by guido on 2/8/15.
 */
public abstract class BuilderBaseRequestWithToken extends BuilderBaseRequest{

    protected RequestPackage makeRequestPackage() {
        RequestPackage requestPackage = super.makeRequestPackage();
        String token = ((IGetToken) object).getToken();
        requestPackage.setHeaderProperty("Authorization", "Token token=" + token);
        return requestPackage;
    }

    public BuilderBaseRequestWithToken()
    {
        super();
    }
}
