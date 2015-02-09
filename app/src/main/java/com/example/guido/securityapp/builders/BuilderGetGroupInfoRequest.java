package com.example.guido.securityapp.builders;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.json.DefaultConverter;
import com.example.guido.securityapp.converters.json.StringEmptyConverter;
import com.example.guido.securityapp.interfaces.IBuildRequestPackage;
import com.example.guido.securityapp.models.TokenTO;
import com.example.guido.securityapp.restful.RequestPackage;

/**
 * Created by guido on 2/2/15.
 */
public class BuilderGetGroupInfoRequest extends BuilderBaseRequestWithToken{


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
