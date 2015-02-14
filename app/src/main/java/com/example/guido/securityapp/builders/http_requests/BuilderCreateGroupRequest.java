package com.example.guido.securityapp.builders.http_requests;

import com.example.guido.securityapp.builders.http_requests.BuilderBaseRequestWithToken;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.params.CreateGroupToParams;
import com.example.guido.securityapp.models.CreateGroupTO;

/**
 * Created by guido on 2/1/15.
 */
public class BuilderCreateGroupRequest extends BuilderBaseRequestWithToken {


    public BuilderCreateGroupRequest()
    {
        super();
    }

    @Override
    protected Converter getConverter()
    {
        return new CreateGroupToParams();
    }

    @Override
    protected String getSpecificUri() {
        return "/api/groups/create";
    }

    @Override
    protected Class getSpecificClass() {
        return CreateGroupTO.class;
    }
}
