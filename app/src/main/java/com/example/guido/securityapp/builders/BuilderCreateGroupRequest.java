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
public class BuilderCreateGroupRequest implements IBuildRequestPackage{

    private String serverUri;
    private CreateGroupTO groupTO;

    public BuilderCreateGroupRequest()
    {
        serverUri = MyApplication.getContext().getString(R.string.sever_url);
    }
    @Override
    public RequestPackage build() throws Exception {
        if(groupTO ==null)
            throw new IllegalArgumentException("Object must be set first");

        RequestPackage requestPackage = new RequestPackage();
        Converter paramsConverter = getConverter();
        paramsConverter.setObject(groupTO);
        requestPackage.setConverter(paramsConverter);
        requestPackage.setHeaderProperty("Content-Type","application/json");
        requestPackage.setHeaderProperty("Accept","application/json");
        requestPackage.setHeaderProperty("Authorization", "Token token=" + groupTO.getToken());
        requestPackage.setUri(getFullUri());

        return requestPackage;
    }

    protected Converter getConverter()
    {
        return new CreateGroupToParams();
    }

    protected String getFullUri()
    {
        return serverUri + "/api/groups/create";
    }

    @Override
    public void setObject(Object objectInput) throws Exception {
        try
        {
            CreateGroupTO maybeGroup = (CreateGroupTO) objectInput;
            this.groupTO = maybeGroup;
        }
        catch (ClassCastException e)
        {
            throw new IllegalArgumentException("I was expecting a "+CreateGroupTO.class.toString()+" but received "+objectInput.getClass().toString());
        }

    }
}
