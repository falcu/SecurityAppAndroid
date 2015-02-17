package com.example.guido.securityapp.builders.services;

import com.example.guido.securityapp.services.ServiceGroupInformation;
import com.example.guido.securityapp.services.ServiceGroupMember;
import com.example.guido.securityapp.services.ServiceSign;

/**
 * Created by guido on 2/17/15.
 */
public class BuilderServiceGroupMember {

    private static ServiceGroupMember serviceGroupMember = null;

    public static ServiceGroupMember build()
    {
        if(serviceGroupMember==null)
        {
            ServiceSign serviceSign =  BuilderServiceSign.buildServiceSign(BuilderServiceSign.SignOptions.SIGN_IN);
            ServiceGroupInformation serviceGroupInformation = BuilderServiceGroup.buildGroupInformationService();
            serviceGroupMember = new ServiceGroupMember(serviceSign,serviceGroupInformation);
        }

        return serviceGroupMember;
    }

    public static void setService(ServiceGroupMember service)
    {
        serviceGroupMember = service;
    }

    public static void clean()
    {
        serviceGroupMember = null;
    }
}
