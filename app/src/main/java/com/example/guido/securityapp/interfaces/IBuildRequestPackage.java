package com.example.guido.securityapp.interfaces;

import com.example.guido.securityapp.restful.RequestPackage;

/**
 * Created by guido on 1/17/15.
 */
public interface IBuildRequestPackage {

    public RequestPackage build() throws Exception;
    public void setObject(Object objectInput) throws Exception;
}
