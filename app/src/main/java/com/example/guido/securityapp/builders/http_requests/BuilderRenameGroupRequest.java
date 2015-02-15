package com.example.guido.securityapp.builders.http_requests;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.params.RenameGroupToParams;
import com.example.guido.securityapp.models.RenameGroupTO;

/**
 * Created by guido on 2/15/15.
 */
public class BuilderRenameGroupRequest extends BuilderBaseRequestWithToken {
    @Override
    protected Class getSpecificClass() {
        return RenameGroupTO.class;
    }

    @Override
    protected Converter getConverter() {
        return new RenameGroupToParams();
    }

    @Override
    protected String getSpecificUri() {
        return "/api/groups/rename";
    }
}
