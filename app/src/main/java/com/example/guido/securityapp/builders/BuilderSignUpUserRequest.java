package com.example.guido.securityapp.builders;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.params.UserToSignInParams;
import com.example.guido.securityapp.converters.params.UserToSignUpParams;
import com.example.guido.securityapp.interfaces.IBuildRequestPackage;
import com.example.guido.securityapp.models.UserTO;
import com.example.guido.securityapp.restful.RequestPackage;

/**
 * Created by guido on 1/17/15.
 */
public class BuilderSignUpUserRequest implements IBuildRequestPackage {

    protected UserTO userTO;
    protected String serverUri;
    public BuilderSignUpUserRequest()
    {
        serverUri = MyApplication.getContext().getString(R.string.sever_url);
    }
    @Override
    public RequestPackage build() throws Exception{
        if(userTO ==null)
            throw new IllegalArgumentException("Object must be set first");

        RequestPackage requestPackage = new RequestPackage();
        Converter paramsConverter = getConverter();
        paramsConverter.setObject(userTO);
        requestPackage.setConverter(paramsConverter);
        requestPackage.setHeaderProperty("Content-Type","application/json");
        requestPackage.setHeaderProperty("Accept","application/json");
        requestPackage.setUri(getFullUri());

        return requestPackage;
    }

    protected String getFullUri()
    {
       return serverUri + "/api/users/create";
    }

    protected Converter getConverter()
    {
        return new UserToSignUpParams();
    }

    @Override
    public void setObject(Object objectInput) throws Exception{
        try
        {
            UserTO maybeUserTO = (UserTO) objectInput;
            this.userTO = maybeUserTO;
        }
        catch (ClassCastException e)
        {
            throw new IllegalArgumentException("I was expecting a userTO, but received "+objectInput.getClass());
        }
    }
}
