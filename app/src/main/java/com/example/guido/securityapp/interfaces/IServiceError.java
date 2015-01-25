package com.example.guido.securityapp.interfaces;

/**
 * Created by guido on 1/25/15.
 */
public interface IServiceError {
    public boolean wasRequestWithError();
    public String getLastErrorMessage();
}
