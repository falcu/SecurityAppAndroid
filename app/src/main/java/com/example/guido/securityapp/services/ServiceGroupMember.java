package com.example.guido.securityapp.services;

import com.example.guido.securityapp.builders.BuilderGroupService;
import com.example.guido.securityapp.builders.BuilderSignService;
import com.example.guido.securityapp.exceptions.MissingDataException;
import com.example.guido.securityapp.models.Group;
import com.example.guido.securityapp.models.SignedUser;

/**
 * Created by guido on 2/8/15.
 */
public class ServiceGroupMember {
    private ServiceSign serviceSign;
    private ServiceGroupInformation serviceGroupInformation;

    public ServiceGroupMember()
    {
        serviceSign = makeServiceSign();
        serviceGroupInformation = makeServiceGroupInformation();
    }

    public boolean IsCurrentUserCreator() throws Exception
    {
        try
        {
            SignedUser user = serviceSign.getSignedUser();
            Group group = serviceGroupInformation.getGroup();
            return(user.getId() == group.getCreatorId());
        }
        catch (MissingDataException e)
        {
            throw new MissingDataException("Not able to retrieve data from local disk, user needs to create account, create/join group or if done already sync data with server");
        }

    }

    public boolean IsRegularMember() throws Exception
    {
        return !IsCurrentUserCreator();
    }

    protected ServiceSign makeServiceSign()
    {
        return BuilderSignService.buildServiceSign(BuilderSignService.SignOptions.SIGN_IN);
    }

    protected ServiceGroupInformation makeServiceGroupInformation()
    {
        return BuilderGroupService.buildGroupInformationService();
    }
}
