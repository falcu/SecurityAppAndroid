package com.example.guido.securityapp.services;

import com.example.guido.securityapp.exceptions.UnableToRetreiveTokenException;

/**
 * Created by guido on 2/1/15.
 */
public class ServiceUserToken {

    private ServiceSign serviceSign;

    public ServiceUserToken(ServiceSign serviceSign)
    {
        this.serviceSign = serviceSign;
    }

    public String getToken() throws Exception
    {
        try
        {
            String token = serviceSign.getSignedUser().getToken();
            return token;
        }
        catch (Exception e)
        {
            throw new UnableToRetreiveTokenException(e.getMessage());
        }

    }
}
