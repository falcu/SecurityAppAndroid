package com.example.guido.securityapp.validators;

import com.example.guido.securityapp.interfaces.IValidate;

/**
 * Created by guido on 1/24/15.
 */
public class EmailValidator implements IValidate{
    private final String error = "The email address is invalid";

    @Override
    public boolean isValid(Object objectToValidate) {
        validateArgument(objectToValidate);

        String stringToValidate = (String) objectToValidate;

        return stringToValidate.contains("@");
    }

    @Override
    public String getError(Object objectToValidate) {
        if(isValid(objectToValidate))
            return "";
        else
            return error;
    }

    private void validateArgument(Object maybeString)
    {
        if(maybeString.getClass()!=String.class)
            throw new IllegalArgumentException("I was expecting a string");
    }
}
