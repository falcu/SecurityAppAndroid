package com.example.guido.securityapp.restful.services;

import com.example.guido.securityapp.interfaces.IBuildRequestPackage;
import com.example.guido.securityapp.models.UserTO;
import com.example.guido.securityapp.restful.HttpManager;
import com.example.guido.securityapp.restful.RequestPackage;

import org.json.JSONObject;

/**
 * Created by guido on 1/17/15.
 */
public class SignHttpService {

    private HttpManager httpManager;
    private IBuildRequestPackage builder;

    public SignHttpService(HttpManager httpManager, IBuildRequestPackage builder)
    {
        this.httpManager = httpManager;
        this.builder = builder;
    }

    public String sign(UserTO userTO) throws Exception{
        builder.setObject(userTO);
        RequestPackage requestPackage = builder.build();
        String jsonData = httpManager.getData(requestPackage);

        return jsonData;

    }
}
