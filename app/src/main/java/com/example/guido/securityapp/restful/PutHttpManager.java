package com.example.guido.securityapp.restful;

/**
 * Created by guido on 1/24/15.
 */
public class PutHttpManager extends PostHttpManager {

    @Override
    protected String getMethod() {
        return "PUT";
    }
}
