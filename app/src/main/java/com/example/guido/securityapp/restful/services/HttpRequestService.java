package com.example.guido.securityapp.restful.services;

import com.example.guido.securityapp.interfaces.IBuildRequestPackage;
import com.example.guido.securityapp.models.CreateGroupTO;
import com.example.guido.securityapp.restful.HttpManager;
import com.example.guido.securityapp.restful.RequestPackage;

/**
 * Created by guido on 2/1/15.
 */
public class HttpRequestService {

    private HttpManager httpManager;
    private IBuildRequestPackage builder;

    public HttpRequestService(HttpManager httpManager,IBuildRequestPackage builder)
    {
        this.httpManager = httpManager;
        this.builder = builder;
    }

    public String request(Object objectTO) throws Exception
    {
        builder.setObject(objectTO);
        RequestPackage requestPackage = builder.build();
        String jsonData = httpManager.getData(requestPackage);

        return jsonData;
    }
}
