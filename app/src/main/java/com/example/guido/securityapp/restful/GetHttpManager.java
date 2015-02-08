package com.example.guido.securityapp.restful;

import com.example.guido.securityapp.exceptions.HttpRequestException;

/**
 * Created by guido on 2/2/15.
 */
public class GetHttpManager extends HttpManager {
    @Override
    public String getData(RequestPackage requestPackage) throws Exception {
        try {
            initializeConnection(requestPackage);
            String data = readData();
            return data;
        }
        catch (Exception e){
            throw new HttpRequestException(e.getMessage());
        }
    }

    @Override
    protected String getMethod() {
        return "GET";
    }
}
