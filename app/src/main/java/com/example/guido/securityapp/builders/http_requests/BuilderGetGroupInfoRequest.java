package com.example.guido.securityapp.builders.http_requests;

import com.example.guido.securityapp.builders.http_requests.BuilderBaseRequestWithToken;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.json.StringEmptyConverter;
import com.example.guido.securityapp.models.TokenTO;

/**
 * Created by guido on 2/2/15.
 */
public class BuilderGetGroupInfoRequest extends BuilderBaseRequestWithToken {


    public BuilderGetGroupInfoRequest()
    {
        super();
    }

    protected Converter getConverter()
    {
        return new StringEmptyConverter();
    }

    @Override
    protected String getSpecificUri() {
        return "/api/groups/group_information?";
    }


    @Override
    protected Class getSpecificClass() {
        return TokenTO.class;
    }
}
