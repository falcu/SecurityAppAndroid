package com.example.guido.securityapp.validators;

import com.example.guido.securityapp.interfaces.IValidate;

/**
 * Created by guido on 4/25/15.
 */
public class EqualsValidator implements IValidate{
    private String error = "No match";


    public EqualsValidator()
    {

    }

    public EqualsValidator(String errorMessage)
    {
        error = errorMessage;
    }

    @Override
    public boolean isValid(Object objectToValidate) {
        if(objectToValidate.getClass()!=ObjectPair.class)
            throw new IllegalArgumentException("I was expecting ObjectPair");

        ObjectPair pair = (ObjectPair) objectToValidate;
        return pair.getObj1().equals(pair.getObj2());
    }

    @Override
    public String getError(Object objectToValidate) {
        if(isValid(objectToValidate))
            return "";
        else
            return error;
    }


}
