package com.example.guido.securityapp.builders;

import com.example.guido.securityapp.services.ServiceSign;
import com.example.guido.securityapp.services.ServiceUserToken;

/**
 * Created by guido on 2/1/15.
 */
public class BuilderServiceUserToken {

    public static ServiceUserToken build()
    {
        ServiceSign serviceSign = BuilderSignService.buildServiceSign(BuilderSignService.SignOptions.SIGN_IN);
        return new ServiceUserToken(serviceSign);
    }
}
