package com.example.guido.securityapp.validators;

import com.example.guido.securityapp.interfaces.IValidate;

/**
 * Created by guido on 1/24/15.
 */
public class DefaultValidator implements IValidate {
    @Override
    public boolean isValid(Object objectToValidate) {
        return true;
    }

    @Override
    public String getError(Object objectToValidate) {
        return "";
    }
}
