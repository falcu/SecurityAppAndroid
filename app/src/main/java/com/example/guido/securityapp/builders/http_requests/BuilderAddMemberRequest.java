package com.example.guido.securityapp.builders.http_requests;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.params.NewMemberToParams;
import com.example.guido.securityapp.models.NewMemberTO;

/**
 * Created by guido on 2/8/15.
 */

public class BuilderAddMemberRequest extends BuilderBaseRequestWithToken {

    public BuilderAddMemberRequest()
    {
        super();
    }

    protected Converter getConverter()
    {
        return new NewMemberToParams();
    }

    @Override
    protected String getSpecificUri() {
        return "/api/groups/add_single_group";
    }

    @Override
    protected Class getSpecificClass() {
        return NewMemberTO.class;
    }
}
