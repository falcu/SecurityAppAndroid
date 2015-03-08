package com.example.guido.securityapp.restful;

import com.example.guido.securityapp.exceptions.HttpRequestException;
import com.example.guido.securityapp.exceptions.HttpTimeOutException;

import java.net.SocketTimeoutException;

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
        catch (SocketTimeoutException e)
        {
            throw new HttpTimeOutException(e.getMessage());
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
