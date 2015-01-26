package com.example.guido.securityapp.validators;

import com.example.guido.securityapp.interfaces.IValidate;

/**
 * Created by guido on 1/25/15.
 */
public class LongStringValidator implements IValidate{
    private int maxLength;
    private String error = "too long";

    public LongStringValidator(int maxLength)
    {
        this.setMaxLength(maxLength);
    }

    @Override
    public boolean isValid(Object objectToValidate) {
        validateArgument(objectToValidate);

        String stringToValidate = (String) objectToValidate;

        return stringToValidate.length() <= maxLength;
    }

    @Override
    public String getError(Object objectToValidate) {
        if(isValid(objectToValidate))
            return "";
        else
            return error;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    private void validateArgument(Object maybeString)
    {
        if(maybeString.getClass()!=String.class)
            throw new IllegalArgumentException("I was expecting a string");
    }
}
