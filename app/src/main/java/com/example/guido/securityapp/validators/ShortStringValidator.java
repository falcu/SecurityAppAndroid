package com.example.guido.securityapp.validators;

import com.example.guido.securityapp.interfaces.IValidate;

/**
 * Created by guido on 1/24/15.
 */
public class ShortStringValidator implements IValidate {
    private String error = "too short";
    private int minLength;

    public ShortStringValidator(int length)
    {
        this.minLength = length;
    }
    @Override
    public boolean isValid(Object objectToValidate) {
        validateArgument(objectToValidate);

        String stringToValidate = (String) objectToValidate;

        return stringToValidate.length() >= minLength;
    }

    @Override
    public String getError(Object objectToValidate) {
        if(isValid(objectToValidate))
            return "";
        else
            return error;
    }

    public void setMinLength(int length)
    {
        this.minLength = length;
    }

    private void validateArgument(Object maybeString)
    {
        if(maybeString.getClass()!=String.class)
            throw new IllegalArgumentException("I was expecting a string");
    }
}
