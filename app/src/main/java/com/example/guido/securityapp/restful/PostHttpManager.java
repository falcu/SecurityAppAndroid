package com.example.guido.securityapp.restful;

import com.example.guido.securityapp.exceptions.HttpRequestException;

import java.io.OutputStreamWriter;

/**
 * Created by guido on 1/17/15.
 */
public class PostHttpManager extends HttpManager {
    @Override
    public String getData(RequestPackage requestPackage) throws Exception{
        try {
            initializeConnection(requestPackage);
            setParams(requestPackage);
            String data = readData();
            return data;
        }
        catch (Exception e){
            throw new HttpRequestException(e.getMessage());
        }
    }

    @Override
    protected String getMethod() {
        return "POST";
    }

    protected void setParams(RequestPackage requestPackage) throws Exception
    {
        connection.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(requestPackage.getParams());
        writer.flush();
    }
}
