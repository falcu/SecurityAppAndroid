package com.example.guido.securityapp.builders;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.json.DefaultConverter;
import com.example.guido.securityapp.interfaces.IBuildRequestPackage;
import com.example.guido.securityapp.restful.RequestPackage;

/**
 * Created by guido on 2/2/15.
 */
public class BuilderGetGroupInfoRequest implements IBuildRequestPackage {

    private String serverUri;
    private String token;

    public BuilderGetGroupInfoRequest()
    {
        serverUri = MyApplication.getContext().getString(R.string.sever_url);
    }
    @Override
    public RequestPackage build() throws Exception {
        if(token ==null)
            throw new IllegalArgumentException("Object must be set first");

        RequestPackage requestPackage = new RequestPackage();
        Converter paramsConverter = getConverter();
        paramsConverter.setObject("");
        requestPackage.setConverter(paramsConverter);
        requestPackage.setHeaderProperty("Content-Type","application/json");
        requestPackage.setHeaderProperty("Accept","application/json");
        requestPackage.setHeaderProperty("Authorization", "Token token=" + token);
        requestPackage.setUri(getFullUri());

        return requestPackage;
    }

    protected Converter getConverter()
    {
        return new DefaultConverter();
    }

    protected String getFullUri()
    {
        return serverUri + "/api/groups/group_information?";
    }

    @Override
    public void setObject(Object objectInput) throws Exception {
        try
        {
            String maybeToken = (String) objectInput;
            this.token = maybeToken;
        }
        catch (ClassCastException e)
        {
            throw new IllegalArgumentException("I was expecting a "+String.class.toString()+" but received "+objectInput.getClass().toString());
        }
    }
}
