package com.example.guido.securityapp.interfaces;

/**
 * Created by guido on 1/24/15.
 */
public interface IValidate {
    public boolean isValid(Object objectToValidate);
    public String getError(Object objectToValidate);
}
