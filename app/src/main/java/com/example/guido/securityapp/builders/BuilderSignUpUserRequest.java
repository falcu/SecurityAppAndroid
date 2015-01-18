package com.example.guido.securityapp.builders;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.params.UserToSignUpParams;
import com.example.guido.securityapp.interfaces.IBuildRequestPackage;
import com.example.guido.securityapp.models.UserTO;
import com.example.guido.securityapp.restful.RequestPackage;

/**
 * Created by guido on 1/17/15.
 */
public class BuilderSignUpUserRequest implements IBuildRequestPackage {

    private UserTO userTO;
    private String serverUri;
    public BuilderSignUpUserRequest()
    {
        serverUri = MyApplication.getContext().getString(R.string.sever_url);
    }
    @Override
    public RequestPackage build() throws Exception{
        if(userTO ==null)
            throw new IllegalArgumentException("UserTO can't be null");

        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod("POST");
        UserToSignUpParams paramsConverter = new UserToSignUpParams();
        paramsConverter.setObject(userTO);
        requestPackage.setConverter(paramsConverter);
        requestPackage.setHeaderProperty("Content-Type","application/json");
        requestPackage.setHeaderProperty("Accept","application/json");
        requestPackage.setUri(serverUri + "/api/users/create");

        return requestPackage;
    }

    @Override
    public void setObject(Object objectInput) throws Exception{
        UserTO maybeUserTO = (UserTO) objectInput;
        if(maybeUserTO ==null)
            throw new IllegalArgumentException("I was expecting a userTO, but received "+objectInput.getClass());

        this.userTO = maybeUserTO;
    }
}
