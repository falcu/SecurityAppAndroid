package com.example.guido.securityapp.builders;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.params.CreateGroupToParams;
import com.example.guido.securityapp.interfaces.IBuildRequestPackage;
import com.example.guido.securityapp.models.CreateGroupTO;
import com.example.guido.securityapp.restful.RequestPackage;

/**
 * Created by guido on 2/1/15.
 */
public class BuilderCreateGroupRequest extends BuilderBaseRequestWithToken{


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
