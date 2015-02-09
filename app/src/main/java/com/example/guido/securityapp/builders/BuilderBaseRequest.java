package com.example.guido.securityapp.builders;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.interfaces.IBuildRequestPackage;
import com.example.guido.securityapp.models.CreateGroupTO;
import com.example.guido.securityapp.models.NewMemberTO;
import com.example.guido.securityapp.restful.RequestPackage;

/**
 * Created by guido on 2/8/15.
 */
public abstract class BuilderBaseRequest implements IBuildRequestPackage{

    protected Class type;
    protected Object object = "";
    protected String serverUri;

    public BuilderBaseRequest()
    {
        type = getSpecificClass();
        serverUri = MyApplication.getContext().getString(R.string.sever_url);
    }

    @Override
    public RequestPackage build() throws Exception {
        if(object ==null)
            throw new IllegalArgumentException("Object must be set first");

        return makeRequestPackage();
    }

    @Override
    public void setObject(Object objectInput) throws Exception {

        if(!objectInput.getClass().equals(type))
        {
            throw new IllegalArgumentException("I was expecting a "+object.getClass().toString()+" but received "+objectInput.getClass().toString());
        }

        this.object = objectInput;
    }

    protected abstract Class getSpecificClass();

    protected abstract Converter getConverter();

    protected String getFullUri()
    {
        return serverUri + getSpecificUri();
    }

    protected abstract String getSpecificUri();

    protected RequestPackage makeRequestPackage()
    {
        RequestPackage requestPackage = new RequestPackage();
        Converter paramsConverter = getConverter();
        paramsConverter.setObject(object);
        requestPackage.setConverter(paramsConverter);
        requestPackage.setHeaderProperty("Content-Type","application/json");
        requestPackage.setHeaderProperty("Accept","application/json");
        requestPackage.setUri(getFullUri());

        return requestPackage;
    }

}
